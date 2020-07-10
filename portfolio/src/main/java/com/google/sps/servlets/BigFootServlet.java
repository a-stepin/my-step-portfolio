package com.google.sps.servlets;

import com.google.sps.data.BigFootSighting;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/** Returns UFO data as a JSON array, e.g. [{"lat": 38.4404675, "lng": -122.7144313}] */
@WebServlet("/bf-data")
public class BigFootServlet extends HttpServlet {

  private Collection<BigFootSighting> bfSightings;

  @Override
  public void init() {
    bfSightings = new ArrayList<>();

    Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/bf-data.csv"));

    while (scanner.hasNextLine()) {
        
        String line = scanner.nextLine();
        String[] cells = line.split(",");
          
          // check that we are past the first row
          if(!cells[4].equals("latitude")){
            double lat = Double.parseDouble(cells[cells.length - 2]);
            double lng = Double.parseDouble(cells[cells.length - 1]);
            bfSightings.add(new BigFootSighting(lat, lng));
          }
    }
    scanner.close();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    Gson gson = new Gson();
    String json = gson.toJson(bfSightings);
    response.getWriter().println(json);
  }
}