package request;


public class registerRequest{

    /**
     * hold username
     */
    private String userName;
    /**
     * holds password
     */
    private String password;
    /**
     * holds email
     */
   private  String email;
    /**
     * holds firstname
     */
    private String firstName;
    /**
     * holds lastname
     */
    private String lastName;
    /**
     * holds the gender
     */
    private String gender;

    /**
     * you know, repeating all of these relatively generic descriptions gets really boring after a while.  you feel me?
     * @param user
     * @param pass
     * @param mail
     * @param first
     * @param last
     * @param gender
     */
    public registerRequest(String user, String pass, String mail, String first, String last, String gender){
        userName = new String(user);
        password = new String(pass);
        email = new String(mail);
        firstName = new String(first);
        lastName = new String(last);
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }


    /**
     * gets all the fun vars we just put into the constructor
     * @return a String containnig all this objects variables.
     */
    public String getBody(){
        return null;
    }



}