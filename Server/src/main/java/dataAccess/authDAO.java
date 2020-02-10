package dataAccess;
import java.sql.*;
import model.*;

/**
 * class authDAO
 * handles all accessing of the Auth database
 */
public class authDAO{

    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;

    public authTokenModel getUserAuth(String personID, String username){

            generateDB();
            String query = " SELECT * FROM authToken WHERE person_ID = ? AND username = ? ";
            try {
                stmt = connection.prepareStatement(query);
                stmt.setString(1, personID);
                stmt.setString(2, username);
                results = stmt.executeQuery();
                authTokenModel m = null;
                String usernam = null;
                String dbAuth = null;
                String person = null;
                while (results.next()) {
                    usernam = results.getString(2);
                    person = results.getString(3);
                    dbAuth = results.getString(1);
                }
                m = new authTokenModel(usernam, person, dbAuth);
                return m;
            }
            catch (Exception e)
            {
                System.out.println("error in getting model and getUserAuth");
                return null;
            }

        }

        public authTokenModel getAuth(String auth){
            generateDB();

            String query = " SELECT * FROM authToken WHERE auth_ID = ?";
            try {
                stmt = connection.prepareStatement(query);
                stmt.setString(1, auth);
                results = stmt.executeQuery();
                authTokenModel m = null;
                String usernam = null;
                String dbAuth = null;
                String person = null;
                while (results.next()) {
                    usernam = results.getString(2);
                    person = results.getString(3);
                    dbAuth = results.getString(1);
                }
                //System.out.println("the authReceived: " + auth);
                //System.out.println("the auth: " + dbAuth);
                m = new authTokenModel(usernam, person, dbAuth);
                return m;
            }
            catch (Exception e)
            {
                System.out.println("error in getting model and getAuth");
                return null;
            }


        }





    public void generateDB(){

        // Open a Database Connection
        if(connection != null)
            return;
        //String dbname = "jdbc:sqlite:fmsdb";
        try {
            Class.forName("org.sqlite.JDBC");
        }catch(Exception ex){
            System.out.println("forname isn't working" + ex.getMessage());
            return;
        }
        String dbname = "jdbc:sqlite:fmsdb";
        try {
            connection = DriverManager.getConnection(dbname);
            String create = "create table if not exists authToken( `auth_ID` TEXT NOT NULL, `username` TEXT NOT NULL, `person_ID` TEXT NOT NULL, PRIMARY KEY(`auth_ID`) )";
            stmt = connection.prepareStatement(create);       // throws SQLException
            stmt.executeUpdate();                             // throws SQLException
            stmt.close();
        }
        catch(Exception ex){
            System.out.println("you done messed up a-a-ron!");
        }
    }

    /**
     * authTokenModel postAuthToken
     * creates a new authToken for that user
     * @param m holds all the necessary info.
     * @return the new authToken
     */
    public authTokenModel postAuthToken(userModel m){
        generateDB();
            authTokenModel r;
        try {
            String username = m.userName;
            String personID = m.personID;
            r = new authTokenModel(username, personID);

            String query = "INSERT INTO authToken values(?,?,?)";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, r.getID());
            stmt.setString(2, username);
            stmt.setString(3, personID);
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println("error in getting model in auth");
            return null;
        }
        return r;
    }

    /**
     * boolean deleteAuthTokens
     * deletes all authTokens.  ever.
     * @return true if successful
     */
    public boolean deleteAuthTokens(){
        generateDB();
            String query = "DELETE FROM authToken ";
        try{
            stmt = connection.prepareStatement(query);
            stmt.executeUpdate();
        }catch(Exception e) {
            System.out.println("an error occurred in deleteAuthTokens");
            return false;
        }
        return true;
        }

}