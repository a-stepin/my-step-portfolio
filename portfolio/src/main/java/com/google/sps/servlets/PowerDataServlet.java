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
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.sps.data.IndianPowerData;
import java.util.Collection;
import java.util.ArrayList;

/** Returns data about energy generation in India as a JSON object*/
@WebServlet("/power-data")
public class PowerDataServlet extends HttpServlet {

  private Collection<IndianPowerData> powerData;

  @Override
  public void init() {
    powerData = new ArrayList<>();
    Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/indian-power-generation.csv"));
    scanner.nextLine(); // Skip CSV header
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] cells = line.split(",");
        String name = String.valueOf(cells[0]);
        Integer area = Integer.valueOf(cells[1]);
        String region = String.valueOf(cells[2]);
        Double percent = Double.valueOf(cells[3]);

        powerData.add(new IndianPowerData(name, area, percent, region));
      
    }
    scanner.close();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    Gson gson = new Gson();
    String json = gson.toJson(powerData);
    response.getWriter().println(json);
  }
}
