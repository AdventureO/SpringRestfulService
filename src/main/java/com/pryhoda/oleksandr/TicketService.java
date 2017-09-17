package com.pryhoda.oleksandr;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class TicketService {

    DataBaseManipulation dbManipulator;
    static int ticketId = 0;

    // Generate unique number and set event for ticket
    public void generateTicketInfo(Ticket ticket, int eventId) throws SQLException {
        ticket.setUniqueNumber(UUID.randomUUID().toString());
        ticket.setEvent(dbManipulator.selectAllEvents(DataBaseManipulation.makeConnection(), eventId).get(eventId));
        ticket.setId(ticketId);
        ticketId++;
    }

    // Get request for all events
    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public ResponseEntity<Map<Integer, Event>> getAllEvents() {

        if (!dbManipulator.isEvent) {
            try {
                dbManipulator.insertEvents(dbManipulator.makeConnection());
                dbManipulator.isEvent = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Map<Integer, Event> eventStorage = new HashMap<Integer, Event>();
        try {
            eventStorage = dbManipulator.selectAllEvents(DataBaseManipulation.makeConnection(), -1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Map<Integer, Event>>( eventStorage, HttpStatus.OK);
    }

    // Get request for event by id
    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET)
    public  ResponseEntity<Event> getEvent(@PathVariable("eventId") int eventId) {
        Event event = new Event();
        try {
            event = dbManipulator.selectAllEvents(DataBaseManipulation.makeConnection(), eventId).get(eventId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    // Post request for registration
    @RequestMapping(value = "/event/{eventId}/register", method = RequestMethod.POST)
    public ResponseEntity<Ticket> addTicket(@PathVariable("eventId") int eventId, @RequestBody Ticket ticket) {
        try {
            generateTicketInfo(ticket, eventId);
            if (dbManipulator.checkTicketAmount(dbManipulator.makeConnection(), eventId)) {
                dbManipulator.insertTicket(dbManipulator.makeConnection(), ticket);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
    }

    // Get request for ticket
    @RequestMapping(value = "/ticket/{ticketId}", method = RequestMethod.GET)
    public  ResponseEntity<Ticket> getTicket(@PathVariable("ticketId") int ticketId) {
        Ticket ticket = new Ticket();
        try {
            ticket = dbManipulator.selectTicket(dbManipulator.makeConnection(), ticketId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
    }
}
