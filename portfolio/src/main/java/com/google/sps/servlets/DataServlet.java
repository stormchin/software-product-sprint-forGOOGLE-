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

package com.google.sps.servlets;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    
    public class Name 
    {
        private ArrayList<String> names = new ArrayList<String>();
    }
  
    private Name name = new Name();

  

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson(); 
        String json = gson.toJson(name);
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        getNames(request);
        response.sendRedirect("https://8080-dot-10831547-dot-devshell.appspot.com/");
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //  Parses information pushed to server from name_input box.
    //  Separates names where ", " is found.
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void getNames(HttpServletRequest request)
    {
        String raw_names = request.getParameter("name_input");
        String [] namesArray = raw_names.split(", ");
        String [] namesFormated = format(namesArray,namesArray.length);
        for(int i=0; i< namesFormated.length;i++)
        {
            name.names.add(namesFormated[i]);
        }
    }

    private String[] format(String[] names,int size)
    {
        for(int i =0; i< size; i++)
        {
            String firstChar = names[i].substring(0,1);
            String lastFewChar = names[i].substring(1);
            firstChar.toUpperCase();
            names[i] = firstChar + lastFewChar;
        }
        return names;
    }


}

