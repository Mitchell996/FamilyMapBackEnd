package response;

import com.google.gson.Gson;

/**
 * since fillResponse is just this, I figured I'd make it a generic setup that gets filled if there's an
 * error in any of the other responses.
 */
public class response{

    /**
     * message to return whether or not it was successful.
     */
    String message;

    /**
     * Generic constructor, just enter the string message.
     * @param m is the message to be returned.
     */
        public response(String m){message = m;}

    /**
     * just here to get rid of all the default constructor errors from child classes
     */
    public response(){}

    /**
     * returns the message
     * @return String message
     */
    public String getBody(){ Gson a = new Gson();
        return a.toJson(this);}

        public String getMessage()
        {
            return message;
        }








}