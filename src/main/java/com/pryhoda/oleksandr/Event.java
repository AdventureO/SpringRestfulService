package com.pryhoda.oleksandr;


public class Event {

    int eventId;
    String eventName;
    String eventDate;

    public Event(String eventName, String eventDate, int eventId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

}
