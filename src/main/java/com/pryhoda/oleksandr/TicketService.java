package com.pryhoda.oleksandr;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class TicketService {

    Map<Integer, Ticket> ticketStorage = new HashMap<Integer, Ticket>();
    Map<Integer, Event> eventStorage = new HashMap<Integer, Event>();
    {
        eventStorage.put(0, new Event("Beach Party", "01/12/17", 0));
        eventStorage.put(1, new Event("Night Party", "28/04/17", 1));
    }

    public void generateTicketInfo(Ticket ticket, int eventId) {
        ticket.setUniqueNumber(UUID.randomUUID());
        ticket.setEvent(eventStorage.get(eventId));
    }

/*    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public ResponseEntity<Map<Integer, Ticket>> getAllTickets() {
        return new ResponseEntity<Map<Integer, Ticket>>( ticketStorage, HttpStatus.OK);
    }*/

    // Get request for all events
    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public ResponseEntity<Map<Integer, Event>> getAllEvents() {
        return new ResponseEntity<Map<Integer, Event>>( eventStorage, HttpStatus.OK);
    }

    // Get request for event by id
    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET)
    public  ResponseEntity<Event> getEvent(@PathVariable("eventId") int eventId) {
        return new ResponseEntity<Event>(eventStorage.get(eventId), HttpStatus.OK);
    }

    // Post request for registration
    @RequestMapping(value = "/event/{eventId}/register", method = RequestMethod.POST)
    public ResponseEntity<Ticket> addTicket(@PathVariable("eventId") int eventId, @RequestBody Ticket ticket) {
        if (eventId <= eventStorage.size() - 1) {
            generateTicketInfo(ticket, eventId);
            ticketStorage.put(ticket.ticketId, ticket);
            return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
        }
        return null;
    }

    // Get request for ticket
    @RequestMapping(value = "/ticket/{ticketId}", method = RequestMethod.GET)
    public  ResponseEntity<Ticket> getTicket(@PathVariable("ticketId") int ticketId) {
        return new ResponseEntity<Ticket>(ticketStorage.get(ticketId), HttpStatus.OK);
    }

    // Delete ticket request
    @RequestMapping(value = "/ticket/{ticketId}", method = RequestMethod.DELETE)
    public  ResponseEntity<Ticket> deleteTicket(@PathVariable("ticketId") int ticketId) {
        Ticket tempTicket = ticketStorage.remove(ticketId);
        return new ResponseEntity<Ticket>(tempTicket, HttpStatus.OK);
    }
}
