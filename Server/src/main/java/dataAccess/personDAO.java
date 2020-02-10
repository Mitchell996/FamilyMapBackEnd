package dataAccess;
import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
/**
 * class personDAO
 * should perform all the necessary CRUD for person
 */
public class personDAO
{
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;


    /**
     * performGetPerson
     * calls the database and gets data about the person.
     * @param personID
     * @param authID
     * @return a personModel with all the data found in the db
     */
    public personModel performGetPerson(String personID, String authID){
                generateDB();
        generateDB();
        ArrayList<personModel> p = new ArrayList<>();
        authDAO auth = new authDAO();
        authTokenModel m = auth.getAuth(authID);
        if(m == null){
            return null;
        }
        String username = m.getUserName();
        //System.out.println("The username given: " + username);
        ArrayList<personModel> toParse = getDescendants(username);

        for(personModel person : toParse)
        {
            //System.out.println(person.getID());
            if(person.getID().equals(personID))
                return person;
        }
        return null;

    }

    /**
     * performGetPersons
     * grabs every person attached to that authID
     * @param authID
     * @return
     */
    public ArrayList<personModel> performGetPersons(String authID){
            generateDB();
                ArrayList<personModel> p = new ArrayList<>();
                authDAO auth = new authDAO();
                authTokenModel m = auth.getAuth(authID);
                String username = m.getUserName();
                return getDescendants(username);
    }

    /**
     *
     * @param username
     * @return arraylist of all descendants of the user.
     */
    public ArrayList<personModel> getDescendants(String username){
        generateDB();
        ArrayList<personModel> p = new ArrayList<>();
        try{

            String query = "SELECT * FROM persons WHERE descendant = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1,username);
            results = stmt.executeQuery();
            personUsersModel pu = null;
            personModel m = null;
            while(results.next()){
                //System.out.println("in the while!");
                //results.getString(1);
                pu = new personUsersModel(results.getString(3), results.getString(4), results.getString(5));
                //public personModel(String DESC, String dad, String mom, String wife, personUsersModel temp, String ID)
                m = new personModel(results.getString(2), results.getString(6), results.getString(7), results.getString(8), pu, results.getString(1));
                //System.out.println("what do we hav here?  " + m.getDescendant());
                p.add(m);
            }
        } catch (Exception e) {
            System.out.println("error in getting descendants! " + e.getMessage());
            return null;
        }

        return p;
    }
    public boolean deleteDescendants(String username)
    {
        generateDB();
        String query = "DELETE FROM persons WHERE descendant = ?";
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
     * personModel perfomrPostPerson
     * puts all the relevant data into the db
     * @param m = the person to enter
     * @return the personModel entered
     */
    public personModel performPostPerson(personModel m){
            generateDB();
        try{
            String insert = "INSERT INTO persons values(?,?,?,?,?,?,?,?)";
        stmt = connection.prepareStatement(insert);
        //System.out.println("posting a person!");
            //System.out.println("hello there " + m.getID());
        stmt.setString(1, m.getID());
        //ystem.out.println("does it have an ID?");
        stmt.setString(2, m.getDescendant());
        stmt.setString(3, m.getFirstName());
        stmt.setString(4, m.getLastName());
        stmt.setString(5, m.getGender());
        //System.out.println("gets past the regular fields");
        if(m.getFather() == null)
            stmt.setNull(6, Types.VARCHAR);
        else {
            //System.out.println("does father anyway");
            stmt.setString(6, m.getFather());
        }

        if(m.getMother() == null)
            stmt.setNull(7, Types.VARCHAR);
        else {
            //System.out.println("does mother anyway");
        stmt.setString(7, m.getMother()); }

        if(m.getSpouse() == null)
            stmt.setNull(8, Types.VARCHAR);
        else {
           // System.out.println("does spouse anyway");
        stmt.setString(8, m.getSpouse()); }

        stmt.executeUpdate();
        stmt.close();
       } catch (Exception e) {
           System.out.println("error in post! " +e.getCause() + " " + e.getMessage());
            return null;
        }
        return m;
    }

    public void generateDB(){

        // Open a Database Connection
        if(connection != null)
            return;

        String dbname = "jdbc:sqlite:fmsdb";
        try {
            Class.forName("org.sqlite.JDBC");
        }catch(Exception ex){
            System.out.println("forname isn't working" + ex.getMessage());
            return;
        }
        try {
            connection = DriverManager.getConnection(dbname);
            String create = "create table if not exists persons( `ID` TEXT NOT NULL, `descendant` TEXT NOT NULL, `first_name` TEXT NOT NULL," +
                    " `last_name` TEXT NOT NULL, `gender` TEXT NOT NULL, `father` TEXT, `mother` TEXT, `spouse` TEXT, PRIMARY KEY(`ID`) )";
            stmt = connection.prepareStatement(create);       // throws SQLException
            stmt.executeUpdate();                             // throws SQLException
            stmt.close();
        }
        catch(Exception ex){
            System.out.println("you done messed up a-a-ron! " + ex.getMessage());
        }
    }


    /**
     * boolean performDeleteAllPersons
     * deletes everything.  ever.
     * @return true if successful
     */
    public boolean performDeleteAllPersons(){
        generateDB();
            String query = "DELETE FROM persons ";
        try{
            stmt = connection.prepareStatement(query);
            stmt.executeUpdate();
        }catch(Exception e) {
            System.out.println("an error occurred in deleting all persons " + e.getMessage());
            return false;
        }
        return true;
    }

}