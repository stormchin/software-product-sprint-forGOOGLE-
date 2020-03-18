// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public final class FindMeetingQuery {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

        if(request.getDuration()>=TimeRange.WHOLE_DAY.duration()+1)
        {
            return Arrays.asList();
        }
        
        if(events.isEmpty() || request.getAttendees().isEmpty())
        {
            return Arrays.asList(TimeRange.WHOLE_DAY);
        }


        Map<String, List<TimeRange>> unavailableTimes = new HashMap<>();

        for (Event event: events) {
            for (String attendee: event.getAttendees()) {
                if (!request.getAttendees().contains(attendee)) {
                    continue;
                }
                if (unavailableTimes.get(attendee) == null) {
                    unavailableTimes.put(attendee, new ArrayList<TimeRange>());
                    unavailableTimes.get(attendee).add(event.getWhen());
                }
                else {
                    unavailableTimes.get(attendee).add(event.getWhen());
                }
            }
        }

        if(unavailableTimes.isEmpty() || request.getAttendees().isEmpty())
                {
                    return Arrays.asList(TimeRange.WHOLE_DAY);
                }

        //Create a collection of TimeRanges to hold all the time ranges of the events requested.
        ArrayList<TimeRange> eventTimes = new ArrayList();
        //Add each time range requested to the array. 
        for (Map.Entry<String, List<TimeRange>> entry : unavailableTimes.entrySet()) 
        {
		    for (TimeRange times : entry.getValue())
            {
                eventTimes.add(times);
            }
		}

        /*
        for (TimeRange time : unavailableTimes) {
            eventTimes.add(specificEvent.getWhen());
        }
        */

        //if there is only one event split the day up accordingly
        if(eventTimes.size() == 1 || eventTimes.get(0).equals(eventTimes.get(1)) )
        {
            // Events  : |----A------|     
            // Day     : |-----------------------------|
            // Options :             |--------1--------|

            if(eventTimes.get(0).start() == 0)
            {
                return(Arrays.asList(TimeRange.fromStartEnd(eventTimes.get(0).end(),TimeRange.END_OF_DAY,true)));
            }
            // Events  :        |----A------|     
            // Day     : |-----------------------------|
            // Options : |---1--|           |----2-----|
            else
            {
                return (Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY,eventTimes.get(0).start(),false),TimeRange.fromStartEnd(eventTimes.get(0).end(),TimeRange.END_OF_DAY,true)));
            }
        }
        else
        {
             if(eventTimes.get(0).start() == 0 && eventTimes.get(1).end() == 1440)
            {
                return(Arrays.asList(TimeRange.fromStartEnd(eventTimes.get(0).end(),eventTimes.get(1).start(),false)));
            }
            //Event 1 overlaps event 2
            if(eventTimes.get(0).overlaps(eventTimes.get(1)) && !(eventTimes.get(0).contains(eventTimes.get(1)) || eventTimes.get(1).contains(eventTimes.get(0))) )
            {
                return (Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY,eventTimes.get(0).start(),false),TimeRange.fromStartEnd(eventTimes.get(1).end(),TimeRange.END_OF_DAY,true)));
            }

            //Event 1 or event 2 contains the other event
            if(eventTimes.get(0).contains(eventTimes.get(1)) || eventTimes.get(1).contains(eventTimes.get(0)))
            {
                if(eventTimes.get(0).start()<eventTimes.get(1).start())
                {
                    return (Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY,eventTimes.get(0).start(),false),TimeRange.fromStartEnd(eventTimes.get(0).end(),TimeRange.END_OF_DAY,true)));
                }
                else
                {
                    return (Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY,eventTimes.get(1).start(),false),TimeRange.fromStartEnd(eventTimes.get(1).end(),TimeRange.END_OF_DAY,true)));
                }
                
            }
            return Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, eventTimes.get(0).start(), false),
                                TimeRange.fromStartEnd(eventTimes.get(0).end(), eventTimes.get(1).start(), false),
                                TimeRange.fromStartEnd(eventTimes.get(1).end(), TimeRange.END_OF_DAY, true));



        }
        
        
        



    //throw new UnsupportedOperationException("TODO: Implement this method.");
  }
}