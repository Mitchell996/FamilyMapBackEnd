package dataAccess;
import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class eventDAO{


    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;



    /**
     * arrayList getEventByPerson
     * gets all the events in db connected by that personID
     * @param personID
     * @return returns arraylist of eventModels from the db
     */
    public ArrayList<eventModel> getEventByPerson(String personID){

        ArrayList<eventModel> toReturn = new ArrayList<eventModel>();
        generateDB(); //so we don't get one of those dang "null pointer exception errors wahhh wahhh wahh
        String query = "SELECT * FROM events WHERE person_ID = ?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, personID);
           //ResultSet rs;
            results = stmt.executeQuery();
            eventModel m = null;
            while(results.next()){
                String person_ID = results.getString(2);
                String ID = results.getString(1);
                String descendant = results.getString(3);
                String latitude = results.getString(4);
                String longitude = results.getString(5);
                String country = results.getString(6);
                String city = results.getString(7);
                String eventType = results.getString(8);
                String year = results.getString(9);
                m = new eventModel(descendant, person_ID, latitude, longitude, country, city, eventType, year,ID );
                toReturn.add(m);
            }
       }
       catch(Exception e) {
           System.out.println("an error occurred in getEventByPerson");
           return null;
       }
       return toReturn;
    }


    public eventModel getEventByID(String eventID){

        ArrayList<eventModel> toReturn = new ArrayList<eventModel>();
        eventModel m = null;
        generateDB(); //so we don't get one of those dang "null pointer exception errors wahhh wahhh wahh
        String query = "SELECT * FROM events WHERE ID = ?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, eventID);
            //ResultSet rs;
            results = stmt.executeQuery();
            //eventModel m = null;
            while(results.next()){
                String person_ID = results.getString(2);
                String ID = results.getString(1);
                String descendant = results.getString(3);
                String latitude = results.getString(4);
                String longitude = results.getString(5);
                String country = results.getString(6);
                String city = results.getString(7);
                String eventType = results.getString(8);
                String year = results.getString(9);
                m = new eventModel(descendant, person_ID, latitude, longitude, country, city, eventType, year,ID );
                toReturn.add(m);
            }
        }
        catch(Exception e) {
            System.out.println("an error occurred in get eventByID");
            return null;
        }
        return m;
    }



    public void generateDB(){

        // Open a Database Connection
        if(connection != null)
            return;
        try {
            Class.forName("org.sqlite.JDBC");
        }catch(Exception ex){
            System.out.println("forname isn't working" + ex.getMessage());
            return;
        }

        String dbname = "jdbc:sqlite:fmsdb";
        try {
            /*connection = DriverManager.getConnection(dbname);
            String drop = "DROP table events";
            stmt = connection.prepareStatement(drop);       // throws SQLException
            stmt.executeUpdate();                             // throws SQLException
            stmt.close();*/
            connection = DriverManager.getConnection(dbname);
            String create = "create table if not exists events( `ID` TEXT NOT NULL, 'person_ID' TEXT NOT NULL, `descendant` TEXT NOT NULL, " +
                    "`latitude` TEXT NOT NULL,`longitude` TEXT NOT NULL, `country` TEXT NOT NULL, `city` TEXT NOT NULL, `event_type` TEXT NOT NULL," +
                    " `year` TEXT NOT NULL, PRIMARY KEY(`ID`) )";
            stmt = connection.prepareStatement(create);       // throws SQLException
            stmt.executeUpdate();                             // throws SQLException
            stmt.close();
        }
        catch(Exception ex){
            System.out.println("failure in generateDB!");
        }
    }



    public boolean deleteEventsByDescendant(String username){
        generateDB();
        String query = "DELETE FROM events WHERE descendant = ?";
        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1,username);
            stmt.executeUpdate();
        }catch(Exception e) {
            System.out.println("an error occurred while deleting" + e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * ArrayList getEventByType
     * grabs all events that match event
     * @param event = the type of event
     * @return all the eventModels that have that eventType
     */


    /**
     * bool postEvent
     * puts a new event into the database
     * @param m contains all the relevant info necessary
     * @return the eventModel edited
     */
    public eventModel postEvent(eventModel m){
            generateDB();
        try{
            String query = " INSERT INTO events values(?,?,?,?,?,?,?,?,?)";
            stmt = connection.prepareStatement(query);
            stmt.setString(2, m.getPersonID());
            stmt.setString(1, m.getID());
            stmt.setString( 3, m.getDescendant());
            stmt.setString(4, m.getLatitude());
            stmt.setString( 5, m.getLongitude());
            stmt.setString( 6, m.getCountry());
            stmt.setString( 7, m.getCity());
            stmt.setString( 8, m.getEventType());
            stmt.setString(9, m.getYear());

            //ResultSet rs;
             stmt.executeUpdate();

        }catch(Exception e) {
            System.out.println("an error occurred in posting in eventDAO " + e.getMessage() );
            return null;
        }

        return m;
    }

    /**
     * bool deleteAllEvents
     * deletes all events in the db.  ever.
     * @return true if successful.
     */
    public boolean deleteAllEvents(){
        generateDB();
        String query = "DELETE FROM events";
        try{
        stmt = connection.prepareStatement(query);
             stmt.executeUpdate();
        }catch(Exception e) {
            System.out.println("an error occurred in deleting " + e.getMessage() );
            return false;
        }

        return true;
    }

}