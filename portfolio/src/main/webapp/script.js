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
    const response = await fetch('/data');
    const quote = await response.text();
    document.getElementById('fetch-container').innerText = quote;
}

/**
 * Fetches stats from the servers and adds them to the DOM.
 */

 /*
function getServerData() {
  fetch('/data').then(response => response.json()).then((stats) => {
    // stats is an object, not a string, so we have to
    // reference its fields to create HTML content

    const statsListElement = document.getElementById('server-data-container');
    statsListElement.innerHTML = '';
    statsListElement.appendChild(
        createListElement('First Message: ' + stats.firstMessage));
    statsListElement.appendChild(
        createListElement('Second Message: ' + stats.secondMessage));
    statsListElement.appendChild(
        createListElement('Third Message: ' + stats.thirdMessage));
  });
}
*/

/**
 * Fetches the current state of the page and builds the UI.
 */
function getComment() {
  fetch('/data').then(response => response.json()).then((comments) => {
    //const totalEl = document.getElementById('total');
    /*
    if (game.gameOver) {
      // The current game is over, show the total for the next game.
      totalEl.innerText = 'Total: 21';
    } else {
      totalEl.innerText = 'Total: ' + game.currentTotal;
    }
    */

    // Build the list of history entries.
    const historyEl = document.getElementById('past-comments');
    if(comments === undefined){
        console.log("Comments are undefined")
    } else {
        console.log(comments)
    }
    if (comments !== undefined){
        comments.forEach((line) => {
        historyEl.appendChild(createListElement(line));
        });
    } else {
        console.log("Still undefined")
    }
  });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
