package handler;
//import request.*;
import service.*;
import response.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;

public class eventHandler {

    public response getEvent(String url, String authToken){
       try {
           String[] urlParts = url.split("/");
           eventService ps = new eventService();
           if (urlParts.length > 2) {
               String id = urlParts[2];
               return ps.getEventByID(id, authToken);
           } else {
               return ps.getEvents(authToken);
           }
       } catch(Exception e) {
           response resp = new response("invalid tokens ");
       }
       return null;
    }
}
