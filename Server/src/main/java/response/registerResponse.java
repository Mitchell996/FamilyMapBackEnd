package response;

import com.google.gson.Gson;

public class registerResponse extends response{

    /**
     * holds authtoken
     */
    String authToken;
    /**
     * holds personID
     */
    String personID;
    /**
     * holds the username
     */
    String username;
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
    public registerResponse(String auth, String person, String user){
        username = user;
        authToken = auth;
        personID = person;

    }

    /**
     * if there's an error, we log the error here.
     * @param m if there's an error
     */
    public registerResponse(String m){
        super(m);
    }
    public String getUsername(){
        return username;
    }
    public String getPersonID(){
        return personID;
    }
    public String getAuthToken(){
        return authToken;
    }
    /**
     * returns the variables that aren't null
     * @return all the vars in string form
     */
    public String getBody(){
        Gson a = new Gson();
        return a.toJson(this);
    }



}