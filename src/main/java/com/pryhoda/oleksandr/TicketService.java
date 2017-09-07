package com.pryhoda.oleksandr;


import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.util.*;

@Controller
@RequestMapping("/ticket")
public class TicketService {

/*    static Set Tickets;

    static {
        Tickets = new HashSet();
        Ticket tick;
        for (int i = 0; i < 10; i++) {
            tick = new Ticket(i, "Ticket" + i);
            //writeInFile(tick);
            Tickets.add(tick);
        }
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET, headers = "Accept=text/html", produces = {"text/html "})
    @ResponseBody
    public String getTicket(@PathVariable int ticketId) {
        Iterator iter = Tickets.iterator();
        while (iter.hasNext()) {
            Ticket f = (Ticket) iter.next();
            if (f.getTicketId() == ticketId) return f.toString();
        }
        return null;
    }*/

    Map<Integer, Ticket> empData = new HashMap<Integer, Ticket>();

    @RequestMapping(value = "/dummy", method = RequestMethod.GET)
    public @ResponseBody Ticket getDummyTicket() {
        Ticket emp = new Ticket(0, "Dummy");
        Ticket emp1 = new Ticket(1, "Vasyl");

        empData.put(emp.ticketId, emp);
        empData.put(emp1.ticketId, emp1);
        return emp;
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    public @ResponseBody Ticket getTicket(@PathVariable("ticketId") int empId) {
        return empData.get(empId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody Ticket createTicket(@RequestBody Ticket emp) {
        empData.put(emp.ticketId, emp);
        return emp;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Ticket> getAllTickets() {
        List<Ticket> emps = new ArrayList<Ticket>();
        Set<Integer> empIdKeys = empData.keySet();
        for(Integer i : empIdKeys){
            emps.add(empData.get(i));
        }
        return emps;
    }

/*    @RequestMapping(method = RequestMethod.GET, headers = "Accept=text/html", produces = {"text/html"})
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

    public static void writeInFile(Ticket ticket) {
        try {
            JSONObject jsonObject = new JSONObject();

            System.out.println(ticket.toString());
            jsonObject.put("Id", ticket.ticketId);
            jsonObject.put("FirstName", ticket.firstName);

            FileWriter fileWriter = new FileWriter("test.json");
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            System.out.println("JSON Object Successfully written to the file!!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

/*    @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Set getTicketsList() {
        return Tickets;
    }*/
}
