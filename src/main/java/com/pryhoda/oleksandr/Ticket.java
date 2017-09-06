package com.pryhoda.oleksandr;


import org.json.simple.JSONObject;
import java.io.FileWriter;

public class Ticket {

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

    public void writeInFile() {
        try {
            JSONObject jsonObject = new JSONObject();

            System.out.println(this.toString());
            jsonObject.put("Id", this.ticketId);
            jsonObject.put("FirstName", this.firstName);

            FileWriter fileWriter = new FileWriter("test.json");
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            System.out.println("JSON Object Successfully written to the file!!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Ticket tick = new Ticket(1, "lol" + 1);
        System.out.println(tick.toString());
        tick.writeInFile();
    }

}
