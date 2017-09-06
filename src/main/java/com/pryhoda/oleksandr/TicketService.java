package com.pryhoda.oleksandr;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Controller
@RequestMapping("/ticket")
public class TicketService {

    static Set Tickets;

    static {
        Tickets = new HashSet();
        Ticket tick;
        for (int i = 0; i < 10; i++) {
            tick = new Ticket(i, "Ticket" + i);
            tick.writeInFile();
            Tickets.add(tick);
        }
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET, headers = "Accept=text/html", produces = {"text/html "})
    @ResponseBody
    public String getTicket(@PathVariable int ticketId) {
        Iterator X = Tickets.iterator();
        while (X.hasNext()) {
            Ticket f = (Ticket) X.next();
            if (f.getTicketId() == ticketId) return f.toString();
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=text/html", produces = {"text/html"})
    @ResponseBody
    public String getTickets() {
        Iterator X = Tickets.iterator();
        String result = "";
        while (X.hasNext()) {
            Ticket f = (Ticket) X.next();
            result += f.toString() + "<br>";
        }
        return result;
    }

/*    @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Set getTicketsList() {
        return Tickets;
    }*/
}
