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

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.google.sps.data.CommentBlock;
import com.google.gson.Gson;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    private CommentBlock comments = new CommentBlock();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    //ArrayList<String> messages = new ArrayList<>();
    //messages.add("Jeff Dean writes directly in binary. He then writes the source code as documentation for other developers.");
    //messages.add("Unsatisfied with constant time, Jeff Dean created the world's first O(1/N) algorithm.");
    //messages.add("Jeff Dean's keyboard has two keys: 1 and 0.");

    // Convert the ArrayList to JSON
    String json = convertToJson(comments.getHistory());

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // If the user sends another POST request after the game is over, then start a new game.
    //if (game.isGameOver()) {
      //game = new SubtractionGame();
    //}

    // Get the input from the form.
    

    String comment = getCommentBody(request);
    if (comment.equals("")) {
      response.setContentType("text/html");
      response.getWriter().println("Please enter a comment.");
      return;
    }

    //game.takePlayerTurn(playerChoice);

    // Redirect back to the HTML page.
    comments.logComment(request.getParameter("name"), comment);
    response.sendRedirect("/index.html");
  }

   /** Returns the choice entered by the player, or -1 if the choice was invalid. */
  private String getCommentBody(HttpServletRequest request) {
    // Get the input from the form.
    String commentBody = request.getParameter("comment");

    return commentBody;
  }


   /**
   * Converts a List of Strings into a JSON string using the Gson library. Note: We first added
   * the Gson library dependency to pom.xml.
   */
  private String convertToJson(List<String> messages) {
    Gson gson = new Gson();
    String json = gson.toJson(messages);
    return json;
  }
}




