package com.pryhoda.oleksandr;

import java.io.Serializable;


public class Ticket implements Serializable{

    int id;
    String firstName;
    String lastName;
    int age;
    Event event;
    String uniqueNumber;

    public Ticket() {
    }

    public Ticket(String firstName, String lastName, int age, Event event, String uniqueNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.event = event;
        this.uniqueNumber = uniqueNumber;
    }

    public Ticket(String firstName, String lastName, int age,  String uniqueNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.uniqueNumber = uniqueNumber;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int ticketId) {
        this.id = ticketId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
