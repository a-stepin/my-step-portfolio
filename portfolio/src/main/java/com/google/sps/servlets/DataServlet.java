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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.google.sps.data.CommentBlock;
import com.google.gson.Gson;

/** Servlet that returns comment data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    private CommentBlock comments = new CommentBlock();

    // If no name is provided, name is set to default
    private final String defaultName = "Anonymous";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        Query query = new Query("Task").addSort("timestamp", SortDirection.DESCENDING);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        String numComString = request.getParameter("numComments");

        int numComments;
        try {
            numComments = Integer.parseInt(numComString);

        } catch (NumberFormatException e) {
            numComments = -1;
        }
        int numComLogged = 0;

        for (Entity entity : results.asIterable()) {
            if(numComments == 0 || numComments > numComLogged){
                String comment = (String) entity.getProperty("comment");
                String name = (String) entity.getProperty("name");
                comments.logComment(name, comment);
            }
            numComLogged++;
        }

        // Send the JSON as the response
        response.setContentType("application/json;");
        response.getWriter().println(convertToJson(comments.getHistory()));
        
    }

  @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String comment = request.getParameter("comment");
        String name = request.getParameter("name");
        System.out.println("Trying to int parse " + request.getParameter("numComments"));

        // Convert the input to an int.
        int numComments;
        try {
            numComments = Integer.parseInt(request.getParameter("numComments"));
        } catch (NumberFormatException e) {
            System.err.println("Could not convert to int: " + request.getParameter("numComments"));
        }

        long timestamp = System.currentTimeMillis();

        // Treat empty name as default name
        if (name.equals("")) {
            name = defaultName;
        }

        Entity taskEntity = new Entity("Task");
        taskEntity.setProperty("name", name);
        taskEntity.setProperty("comment", comment);
        taskEntity.setProperty("timestamp", timestamp);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(taskEntity);

        // Log the comment and redirect back to the HTML page.
        response.sendRedirect("/index.html");
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




