package com.example.mobile_assignment_2.community;

public class Event {
    private String event_name, event_time, event_location, author;

    public Event(String event_name, String event_time, String event_location, String author) {
        this.event_name = event_name;
        this.event_time = event_time;
        this.event_location = event_location;
        this.author = author;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_location() {
        return event_location;
    }

    public String getAuthor() {
        return author;
    }

    public String getEvent_time() {
        return event_time;
    }
}
