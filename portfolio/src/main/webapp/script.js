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

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['I skipped a grade!', "I'm scuba certified!", 'I code in Python and C++!', 'Working at Google is my dream job!','I love Thai food!',"Monopoly is my least favorite game.","I love working out!","I don't have a twitter or facebook.","I was born in Atlanta, Georgia.","I visit Jamaica every year."];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}


function getName() {
    var i;
    fetch('/data').then(response => response.json()).then((jsonFile) => {
    // stats is an object, not a string, so we have to
    // reference its fields to create HTML content
    const messagesListElement = document.getElementById('messages');
    messagesListElement.innerHTML = '';
    for(i=0; i<jsonFile.names.length;i++)
    {
    messagesListElement.appendChild(createListElement(jsonFile.emails[i]+ ": "+jsonFile.names[i]));
    }
  });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

function getLogin() {
    //gets login status
    fetch('/login').then(response => response.json()).then((jsonFile) => {
    const divTag = document.getElementById('loginForm');
    divTag.innerHTML = '';
    if (!jsonFile.login)
    {
        var loginUrl = jsonFile.loginUrl;
        var link = document.createElement("a");
        link.setAttribute('href', loginUrl);
        link.innerHTML ="Login to comment!";
        divTag.appendChild(link);
    }
    else
    {
        console.log("Hello")
        //Creates form to submit comments
        divTag.innerHTML = "<form action='/data' method='POST'> <p>Enter Your Name:</p><input type='text' name ='name_input'><br/><br/><input type='submit'/></form>";
    }
    
  });
}

function start()
{
    getName();
    getLogin();
}