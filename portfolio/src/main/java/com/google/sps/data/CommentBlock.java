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

package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a block of comments, where players take turns subtracting from 21 to reach 0.
 *
 */
public class CommentBlock {

  /** List of descriptions of comments, e.g. "Anonymous said: [new line] 'Hello World' " */
  private final List<String> history = new ArrayList<>();

  public List<String> getHistory(){ return history; }

  public void logComment(String name, String comment) {
    String newComent = String.format("%1$s said: \n '%2$s'", name, comment);
    history.add(newComent);
  }
}
