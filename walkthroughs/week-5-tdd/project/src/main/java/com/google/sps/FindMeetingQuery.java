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

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;


public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    
    // A list of all the events that contain all the necessary attendees
    List<TimeRange> eventTimes = new ArrayList<>();
    //Collections.sort(events, TimeRange.ORDER_BY_START);

    for(Event e : events){
        // if the event has a host that's necessary add it
        if(eventHasAttendee(request.getAttendees(), e.getAttendees())){
            eventTimes.add(e.getWhen());
        }
    }

    Collections.sort(eventTimes, TimeRange.ORDER_BY_START);
    
    // Track the start of the current potential break
    int currStart = TimeRange.START_OF_DAY;

    // Store all previously found breaks
    Collection<TimeRange> resultTimes = new ArrayList<>();

    // Loop through events and find breaks that are the necessary duration
    for(TimeRange t : eventTimes){
        if(currStart + request.getDuration() <= t.start()){
            resultTimes.add(TimeRange.fromStartEnd(currStart, t.start(), false));
        }

        currStart = Math.max(currStart, t.start() + t.duration());
    }

    // Check the last event against the end of day
    if(currStart + request.getDuration() <= TimeRange.END_OF_DAY){
        resultTimes.add(TimeRange.fromStartEnd(currStart, TimeRange.END_OF_DAY, true));
    }

    return resultTimes;
    
  }

  // Returns whether a collection of event attendees has a necessary attendee
  private boolean eventHasAttendee(Collection<String> necessaryAttendees, Set<String> eventAttendees){
      Set<String> allAttendees = new HashSet<>(eventAttendees);
      allAttendees.addAll(necessaryAttendees);
      return(allAttendees.size() != necessaryAttendees.size() + eventAttendees.size());
  }
}
