package com.pryhoda.oleksandr;


import java.io.Serializable;

public class Ticket implements Serializable{

    int ticketId;
    String firstName;

    public Ticket(int ticketId, String firstName) {
        this.ticketId = ticketId;
        this.firstName = firstName;
    }
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String toString() {
        return this.firstName + " " + this.ticketId;
    }

}
