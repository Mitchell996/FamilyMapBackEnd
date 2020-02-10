package response;
import com.google.gson.Gson;

import java.util.ArrayList;
import model.*;
/**
 * this adds all of the events into one single response
 */
public class eventsResponse extends response{

        public ArrayList<eResponse> data;

        /**
         * constructor eventsResponse
         * takes a ArrayList of indivdual events and puts them into a single response
         * //@param newThing the collected event responses to send back
         */
       /* public eventsResponse(ArrayList<eResponse> newThing){
                events = newThing;
        }*/

        public eventsResponse(ArrayList<eventModel> m) {
                data = new ArrayList<eResponse>();
                for(eventModel e : m){
                        data.add(new eResponse(e));
                }

        }


        public eventsResponse(String m){super(m);}

        public String getBody(){
                Gson a = new Gson();
                return a.toJson(this);
        }


}