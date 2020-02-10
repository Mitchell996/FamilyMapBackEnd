package response;

import com.google.gson.Gson;

public class userResponse extends response{

    /**
     * hold username
     */
    public String username;
    /**
     * holds password
     */
    public String password;
    /**
     * holds email
     */
    public String email;
    /**
     * holds firstname
     */
    public String firstName;
    /**
     * holds lastname
     */
    public String lastName;
    /**
     * holds the gender
     */
    public String gender;
   public String personID;

    /**
     * fills in all the values;
     * @param user username
     * @param pass password
     * @param mail email
     * @param first firstName
     * @param last last name
     * @param gen gender
     * @param person personID
     */
    public userResponse(String user, String pass, String mail, String first,String last,String gen,String person){

        username = user;
        password = pass;
        email = mail;
        firstName = first;
        lastName = last;
        gender = gen;
        personID = person;
    }

    /**
     * does the thing that every getBody does.
     * @return
     */
    public String getBody(){ Gson a = new Gson();
        return a.toJson(this);}

}