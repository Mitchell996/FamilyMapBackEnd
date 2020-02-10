package response;


import com.google.gson.Gson;

public class loginResponse extends response {

    /**
     * holds authtoken
     */
    String authToken;
    /**
     * holds personID
     *
     */
    String username;
    String personID;
    /**
     * holds the username
     */

    /**
     * if there's an error, the error message is stored here.
     */
    //String message;

    /**
     * if successful, it'll use this constructor to get the correct body
     * @param auth
     * @param person
     * @param user
     */
    public loginResponse(String auth, String person, String user)
    {
        username = user;
        personID = person;
        authToken = auth;
    }

    /**
     * if there's an error, we log the error here.
     * @param m the error message
     */
    public loginResponse(String m){super(m);}

    /**
     * returns the variables that aren't null
     * @return a String containing all the vars
     */
    public String getBody(){
        Gson a = new Gson();
        return a.toJson(this);
    }






}