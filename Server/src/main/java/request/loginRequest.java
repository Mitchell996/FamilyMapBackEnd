package request;

/**
 * loginRequest
 * used to pass info between the handler and the service
 */
public class loginRequest{


    /**
     * given username
     */
    private String userName;
    /**
     * given password
     */
    private String password;

    /**
     * request constructor
     * just takes in the two globals for the requestbody
     * @param user
     * @param word
     */
    public loginRequest( String user, String word)
    {
        userName = new String(user);
        password = new String(word);

    }

    /**
     * String getBody
     * just sends both strings as one string;
     * @return both username and password
     */
    public String getBody(){
        String body = userName + "\n" + password;
        return body;
    }
    public String getUsername(){
        return userName;
    }
    public String getPassword(){
        return password;
    }





}