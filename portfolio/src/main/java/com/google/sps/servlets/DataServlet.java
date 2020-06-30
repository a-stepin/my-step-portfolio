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

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    ArrayList<String> messages = new ArrayList<>();
    messages.add("Jeff Dean writes directly in binary. He then writes the source code as documentation for other developers.");
    messages.add("Unsatisfied with constant time, Jeff Dean created the world's first O(1/N) algorithm.");
    messages.add("Jeff Dean's keyboard has two keys: 1 and 0.");

    // Convert the ArrayList to JSON
    String json = convertToJson(messages);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);

    //response.getWriter().println("<h1>Hello Alicia!</h1>");
  }


  /**
   * Converts a List of Strings into a JSON string using manual String concatentation.
   */
  private String convertToJson(ArrayList<String> messages) {
    String json = "{";
    json += "\"firstMessage\": ";
    json += "\"" + messages.get(0) + "\"";
    json += ", ";
    json += "\"secondMessage\": ";
    json += "\"" + messages.get(1) + "\"";
    json += ", ";
    json += "\"thirdMessage\": ";
    json += "\"" + messages.get(2) + "\"";
    json += "}";
    return json;
  }
}
