package com.example.android_teliki_v2;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

public class EventGrader {
    // Constants for time difference thresholds
    private static final long THREE_HOURS_IN_MILLIS = 3 * 60 * 60 * 1000; // 3 hours in milliseconds
    private static final long TWO_HOURS_IN_MILLIS = 2 * 60 * 60 * 1000; // 2 hours in milliseconds
    private static final long ONE_HOUR_IN_MILLIS = 60 * 60 * 1000; // 1 hour in milliseconds

    // Method to calculate points for an event based on given criteria
    public static int calculatePoints(Event event, List<Event> allEvents) {
        int points = 0;
        StringBuilder messageBuilder = new StringBuilder(); // Use StringBuilder to concatenate messages

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // Parse the event's timestamp string to obtain a Date object
        try {
            Date eventDate = dateFormat.parse(event.getTimestamp());
            // Get the Calendar instance and set it to the event's date
            Calendar eventCalendar = Calendar.getInstance();
            eventCalendar.setTime(eventDate);

            for (Event otherEvent : allEvents) {
                Date otherEventDate = dateFormat.parse(otherEvent.getTimestamp());

                // Get the Calendar instance and set it to the other event's date
                Calendar otherEventCalendar = Calendar.getInstance();
                otherEventCalendar.setTime(otherEventDate);
                if (event.getEvent().equals(otherEvent.getEvent()) &&
                        event.getUsername()!=(otherEvent.getUsername()) &&
                        event.getLocation().equals(otherEvent.getLocation()) &&
                        isSameDay(eventCalendar, otherEventCalendar)) {
                    points++; // Add 1 point for same event type ,location ,day


                    // Calculate time difference
                    long timeDifference = Math.abs(eventDate.getTime() - otherEventDate.getTime());

                    if (timeDifference <= ONE_HOUR_IN_MILLIS) {
                        points += 3; // Add 3 points for time difference less than or equal to 1 hour
                    } else if (timeDifference <= TWO_HOURS_IN_MILLIS) {
                        points += 2; // Add 2 points for time difference between 1 and 2 hours
                    } else if (timeDifference <= THREE_HOURS_IN_MILLIS) {
                        points += 1; // Add 1 point for time difference between 2 and 3 hours
                    }
                }

                // Check if events have same info but from different users
                if (event.getUsername()!=(otherEvent.getUsername()) &&
                        event.getEvent().equals(otherEvent.getEvent()) &&
                        event.getLocation().equals(otherEvent.getLocation())) {
                    points++; // Add 1 point for same info from different users
                }
            }
        } catch (ParseException e) {
            // Handle ParseException (e.g., log the error, display an error message)
            e.printStackTrace(); // Print the stack trace for debugging
        }

        return points;
    }
    // Helper method to check if two Calendar instances represent the same day
    private static boolean isSameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

}


