package model;
import java.security.SecureRandom;
/**
 * class authToken
 * is used in order to help know if the person has authorization, or needs to sign in again.
 */
public class authTokenModel extends Model{

    /**
     * String auth
     * randomly generated and stored to confirm that this person is authorized.
     */
    private String auth;
    /**
     * String userName
     * to be retrieved from the User object
     */
    private String userName;
    /**
     * String personID
     * The id of the person associated with this token
     */
    public String personID;

    /**
     * constructor authToken
     * @param username: username from object user
     * @param person: from object person
     */
    public authTokenModel(String username, String person)
    {
        SecureRandom random = new SecureRandom();
        auth = getID();
        personID = person;
        userName = new String(username);
    }
    public authTokenModel(String username, String person, String authID)
    {

        SecureRandom random = new SecureRandom();
        auth = getID();
        personID = person;
        userName = new String(username);
        auth = authID;
    }
    public String getAuth(){
        return auth;
    }
    public String getUserName(){
        return userName;
    }

    /**
     * boolean validateAuth
     * checks to see if auth and toValidate are the same
     * @param toValidate holds the authToken to validate
     * @return if the Strings are equal
     */
    public boolean validateAuth( String toValidate ){
        return toValidate.equals(auth);
    }

    /**
     * boolean validatePerson
     * checks if personID and string are the same
     * @param toValidate holds the personID to validate
     * @return toValidate.equals(personID);
     */
    public boolean validatePerson( String toValidate ){
        return toValidate.equals(personID);
    }

    /**
     * boolean validateUser
     * checks if username matches string given
     * @param toValidate holds the username to validate
     * @return toValidate.equals(userName);
     */
    public boolean validateUser( String toValidate ){
        return toValidate.equals(userName);
    }
}