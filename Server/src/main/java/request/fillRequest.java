package request;

public class fillRequest{
    /**
     * String userName
     * used to hold the username to fill the descendants of
     */
    String username;
    /**
     * int numGenerations
     * holds the number of generations to create
     */
    int numGenerations;

    /**
     * constructor fillRequest
     * puts the two values into an object
     * @param user
     * @param num
     */
    public fillRequest(String user, int num)
    {
        username = new String(user);
        numGenerations = num;
    }

    /**
     * String getBody()
     * puts the two values into a String to return
     * @return a String holding the values in username and numGenerations
     */
    public String getBody(){
        String body = username + "\n" + numGenerations;
        return body;
    }

    /**
     * int getNumGenerations
     * generic getter
     * @return numGenerations
     */
    public int getNumGenerations(){return numGenerations;}

    /**
     * String getUserName
     * generic getter
     * @return username
     */
    public String getUsername(){return username;}




}