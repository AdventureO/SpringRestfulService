package com.pryhoda.oleksandr;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataBaseManipulation {

    static boolean isEvent = false;

    static String eventTable = "EVENT(" +
            "event_id int NOT NULL PRIMARY KEY," +
            "name VARCHAR(255), " +
            "date VARCHAR (255)," +
            "ticket_amount int" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8";

    static String ticketTable = "TICKET(" +
            "ticket_id INT PRIMARY KEY ," +
            "event_id INT NOT NULL," +
            "first_name varchar(255)," +
            "last_name varchar(255)," +
            "age int," +
            "unique_number varchar(255)," +
            "FOREIGN KEY (event_id) REFERENCES EVENT(event_id) ON DELETE CASCADE)" +
            " ENGINE=InnoDB DEFAULT CHARSET=utf8";

    DataBaseManipulation () {}

    // Create connection to database
    public static Connection makeConnection() {
        Properties properties = new Properties();
        properties.setProperty("user", "sa");
        properties.setProperty("password", "");
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");

        Statement statement = null;
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:file:./home/oleksandr/Desktop/SpringRestfulService/restServiceDB1;MODE=mysql", properties);
            statement = connection.createStatement();

            statement.execute("create table if not EXISTS " + eventTable);
            System.out.println("Event table created successfully!");

            statement.execute("create table if not EXISTS " + ticketTable);
            System.out.println("Ticket table created successfully!");

            statement.close();

        } catch (ClassNotFoundException except) {
            except.printStackTrace();

        } catch (SQLException except) {
            except.printStackTrace();
        }


        return connection;
    }

    // Insert events into database
    public static void insertEvents(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        String sql1 = "INSERT INTO EVENT (event_id, name, date, ticket_amount) " + "VALUES (0, 'Night Party', '15/02/2017', 1)";
        String sql2 = "INSERT INTO EVENT (event_id, name, date, ticket_amount) " + "VALUES (1, 'Google Meet Up', '01/05/2017', 15)";
        String sql = "INSERT INTO EVENT (event_id, name, date, ticket_amount) " + "VALUES (2, 'Python training', '08/12/2017', 15)";

        statement.executeUpdate(sql1);
        statement.executeUpdate(sql2);
        statement.executeUpdate(sql);

        statement.close();
        connection.close();

    }

    public static Map<Integer, Event> selectAllEvents(Connection connection, int eventId) throws SQLException {

        Statement statement = connection.createStatement();
        Map<Integer, Event> allEvents = new HashMap<Integer, Event>();
        String sql;

        if (eventId >= 0) {
            sql = "SELECT * FROM EVENT WHERE event_id = " + String.format("%d", eventId);
        } else {
            sql = "SELECT * FROM EVENT";
        }

        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()) {
            Event event = new Event();
            event.setId(rs.getInt("event_id"));
            event.setEventName(rs.getString("name"));
            event.setEventDate(rs.getString("date"));
            allEvents.put(event.id, event);
        }

        connection.close();
        statement.close();

        return allEvents;
    }

    public static Ticket selectTicket(Connection connection, int ticketId) throws SQLException {

        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM TICKET WHERE ticket_id = " + String.format("%d", ticketId);

        ResultSet rs = statement.executeQuery(sql);
        Ticket ticket = new Ticket();

        while(rs.next()) {
            ticket.setId(rs.getInt("ticket_id"));
            int eventId = rs.getInt("event_id");
            ticket.setEvent(selectAllEvents(makeConnection(), eventId).get(eventId));
            ticket.setFirstName(rs.getString("first_name"));
            ticket.setLastName(rs.getString("last_name"));
            ticket.setAge(rs.getInt("age"));
            ticket.setUniqueNumber(rs.getString("unique_number").toString());
        }

        connection.close();
        statement.close();

        return ticket;
    }

    public static void insertTicket(Connection connection, Ticket ticket) throws SQLException {

/*        String insertTableSQL = "INSERT INTO TICKET (ticket_id, event_id, first_name, last_name, age, unique_number)  VALUES"
                + "(?,?,?,?,?,?,?)";*/

        Statement statement = connection.createStatement();

        statement.execute("INSERT INTO TICKET (ticket_id, event_id, first_name, last_name, age, unique_number) VALUES('"+ticket.id+"','"+ticket.event.id
                +"','"+ticket.firstName+"','"+ticket.lastName+"','"+ticket.age+"','"+ticket.uniqueNumber+"')");

        /*PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
        preparedStatement.setInt(1, ticket.id);
        preparedStatement.setInt(2, ticket.event.id);
        preparedStatement.setString(3, ticket.firstName);
        preparedStatement.setString(4, ticket.lastName);
        preparedStatement.setInt(5, ticket.age);
        preparedStatement.setString(7, ticket.uniqueNumber);
        preparedStatement.executeUpdate();*/

        connection.close();
        statement.close();
        //preparedStatement.close();
    }

    public static boolean checkTicketAmount(Connection connection, int eventId) throws SQLException {

        Statement statement = connection.createStatement();

        String changeTicketAmount = "UPDATE EVENT SET ticket_amount = ticket_amount - 1 WHERE event_id = " + String.format("%d", eventId);
        String checkTicketAmount = "SELECT ticket_amount FROM EVENT WHERE event_id = " + String.format("%d", eventId);


        ResultSet rs = statement.executeQuery(checkTicketAmount);

        int ticket_amount = 0;
        while(rs.next()) {

            ticket_amount = rs.getInt("ticket_amount");
        }



        if (ticket_amount > 0) {
            statement.executeUpdate(changeTicketAmount);
            statement.close();
            connection.close();
            return true;
        } else {
            statement.close();
            connection.close();
            return false;
        }
    }
}
