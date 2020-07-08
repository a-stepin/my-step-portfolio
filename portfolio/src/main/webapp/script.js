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
      ['We\'ve eased each other\'s boredom for quite a while... It\'s been quite fun.', 'He who strikes first wins.', 'In all things, one cannot win with defense alone. To win, you must attack.'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/**
 * Adds content from the server to the page
 */
async function addData() {
    const response = await fetch("data");
    const quote = await response.text();
    document.getElementById('fetch-container').innerText = quote;
}


/**
 * Fetches the current state of the page and builds the UI.
 */
function getComment() {
    
    let numCom = () => {
        let value = document.getElementById('numComments').value;
        if(value == undefined || value == ""){
            value = 1;
        }

        console.log(value);
        fetch(`/data?numComments=${value}`).then(response => response.json()).then((comments) => {
            // Build the list of history entries.
            let historyEl = document.getElementById('past-comments');
            while(historyEl.hasChildNodes()) {
                try {
                    historyEl.removeChild('li');
                } catch (e) { }
            }
            let valPosted = 0;
            if (comments !== undefined){
                while(valPosted < value){
                    comments.forEach((line) => {
                        historyEl.appendChild(createListElement(line));
                    });
                    valPosted++;
                }
            } 
        }).catch((e) => {
            console.log(e);
        });
    };
    document.getElementById('numComments').onchange = numCom;
    numCom(); 

    /*
    let comVal = document.getElementById('numComments').value;
    console.log(comVal)
    fetch(`/data?numComments=${comVal}`).then(response => response.json()).then((comments) => {
        const historyEl = document.getElementById('past-comments');
        comments.forEach((line) => {
            historyEl.appendChild(createListElement(line));
        });
    }); 
    */addData
}

function setValComments(e){
    console.log(e.sender.value)
}



function clearNumChoice() {
    document.getElementById('past-comments').innerHTML = "";
    let value = document.getElementById('numComments').value;

    fetch('/data?numComments=' + value).then(response => response.json()).then((comments) => {
            // Build the list of history entries.
            let historyEl = document.getElementById('past-comments');
            while(historyEl.hasChildNodes()) {
                try {
                    historyEl.removeChild('li');
                } catch (e) { }
            }
            let valPosted = 0;
            if (comments !== undefined){
                 while(valPosted < value){
                    comments.forEach((line) => {
                        historyEl.appendChild(createListElement(line));
                    });
                    valPosted++;
                } 
                comments.forEach((line) => {
                    historyEl.appendChild(createListElement(line));
                });
            } 
    });
}

async function deleteComments() {
    console.log("Deleting comments");

    const response = await fetch('/delete-data');
    const quote = await response.text();
    document.getElementById('past-comments').innerText = quote;
    console.log("Deleted comments");
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('p');
  liElement.innerText = text;
  return liElement;
}

