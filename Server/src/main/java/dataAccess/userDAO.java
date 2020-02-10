package dataAccess;
import java.util.ArrayList;
import java.sql.*;
import model.*;


public class userDAO{

    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;

    public ArrayList<userModel> getAllUsers(){
        generateDB(); //so we don't get one of those dang "null pointer exception errors wahhh wahhh wahh
        ArrayList<userModel> uModels = new ArrayList<>();
        String query = "SELECT * FROM user";
        userModel m = null;
        try {
            stmt = connection.prepareStatement(query);
           // stmt.setString(1, username);
            //stmt.setString(2, password);
            results = stmt.executeQuery();

            personUsersModel pu;
            while (results.next()) {
                String name = results.getString(1);
                String personID = results.getString(2);
                String firstName = results.getString(3);
                String lastName = results.getString(4);
                String email = results.getString(5);
                String gender = results.getString(6);
                String pword = results.getString(7);
                pu = new personUsersModel(firstName, lastName, gender);
                m = new userModel(name, pword, email, personID, pu);
                uModels.add(m);
            }
            results.close();
            return uModels;
        }
        catch(Exception ex){
            System.out.println("error in getAllUsers");
        }

        return null;
    }



    /**
     * userModel getUser
     * gets all the user data associated with the username
     * @param username should match the userModel' username
     * @param password should match the userModel's password
     * @return the usermodel found
     */
    public userModel getUser(String username, String password) {
        generateDB(); //so we don't get one of those dang "null pointer exception errors wahhh wahhh wahh
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        userModel m = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            results = stmt.executeQuery();

            personUsersModel pu;
            while (results.next()) {
                String name = results.getString(1);
                String personID = results.getString(2);
                String firstName = results.getString(3);
                String lastName = results.getString(4);
                String email = results.getString(5);
                String gender = results.getString(6);
                String pword = results.getString(7);
                pu = new personUsersModel(firstName, lastName, gender);
                m = new userModel(name, pword, email, personID, pu);
            }
            results.close();
                return m;
            }
        catch(Exception ex){
            System.out.println("error in getUser(username/password");
        }

    return null;
    }
    public userModel getUser(String username) {
        generateDB(); //so we don't get one of those dang "null pointer exception errors wahhh wahhh wahh
        String query = "SELECT * FROM user WHERE username = ?";
        userModel m = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            //stmt.setString(2, password);
            results = stmt.executeQuery();

            personUsersModel pu;
            while (results.next()) {
                String name = results.getString(1);
                String personID = results.getString(2);
                String firstName = results.getString(3);
                String lastName = results.getString(4);
                String email = results.getString(5);
                String gender = results.getString(6);
                String pword = results.getString(7);
                pu = new personUsersModel(firstName, lastName, gender);
                m = new userModel(name, pword, email, personID, pu);
            }
            results.close();
            return m;
        }
        catch(Exception ex){
            System.out.println("error in getUser(username");
        }

        return null;
    }
    public void generateDB(){

        // Open a Database Connection

        String dbname = "jdbc:sqlite:fmsdb";
        try {
            Class.forName("org.sqlite.JDBC");
        }catch(Exception ex){
           // System.out.println("forname isn't working" + ex.getMessage());
        return;
        }
        try{
            //System.out.print("for name isn't the issue");
            connection = DriverManager.getConnection(dbname);
            String create = "create table if not exists user( `username` TEXT NOT NULL UNIQUE, " +
                    "`person_ID` TEXT NOT NULL, `first_name` TEXT NOT NULL, `last_name` TEXT NOT NULL," +
                    " `email` TEXT NOT NULL, `gender` TEXT NOT NULL, `password` TEXT NOT NULL, PRIMARY KEY(`username`) )";
            stmt = connection.prepareStatement(create);       // throws SQLException
            stmt.executeUpdate();                             // throws SQLException
            stmt.close();
        }
        catch(Exception ex){
            System.out.println("error:" + ex.getMessage());
        }
    }

    /**
     *boolean deleteUsers
     * deletes all the users.  ever.
     * @return true if successful
     */
    public boolean deleteUsers(){
        generateDB();
        String query = "DELETE FROM USER";
        try{
            stmt = connection.prepareStatement(query);
            stmt.executeUpdate();
        }
        catch(Exception ex){
            System.out.println("hello there " + ex.getMessage());
            return false;
        }
            return true;
    }

    public ArrayList<String> getUserNames() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("weird af");
        }
        generateDB();
        String query = "SELECT username FROM user";
        ArrayList<String> usernames = new ArrayList<String>();
        try {
            PreparedStatement declaration = connection.prepareStatement(query);
            // stmt = connection.prepareStatement(query);
            ResultSet rs = null;
            rs = declaration.executeQuery();
           // declaration.close();
            while (rs.next()) {

                String newFriend = rs.getString(1);
                usernames.add(newFriend);
            }
        }
            catch(Exception ex){
                System.out.println("error in getUserNames");
            }
            return usernames;

    }

    /**
     * userModel postUser
     * puts user into the db
     * @param m is the user being inserted
     * @return the userModel just inserted.
     */
    public userModel postUser(userModel m){
        generateDB();
        String insert = "INSERT INTO user values(?,?,?,?,?,?,?)";
        try {
            stmt = connection.prepareStatement(insert);
            stmt.setString(1, m.userName);
            stmt.setString(2, m.personID);
            stmt.setString(3, m.getFirstName());
            stmt.setString(4, m.getLastName());
            stmt.setString(5, m.email);
            stmt.setString(6, m.getGender());
            stmt.setString(7, m.password);
            stmt.executeUpdate();                             // throws SQLException
            stmt.close();
        }
        catch(Exception ex){
            System.out.println("word to your mother " +ex.getMessage());
            return null;
        }
        return m;
    }

}