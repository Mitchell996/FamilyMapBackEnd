package response;
import model.*;
import com.google.gson.Gson;

public class eResponse extends response{

    public String eventID;
    public String descendant;
    public String personID;
    public String latitude;
    public String longitude;
    public String country;
    public String city;
    public String eventType;
    public String year;

    /**
     * constructor eResponse, just makes an event response, and will be coupled by eventResponse
     * @param event eventID
     * @param desc descendant
     * @param person personID
     * @param lat latittude
     * @param longi longitude
     * @param countr country
     * @param type eventType
     * @param ano year
     */
    public eResponse(String event, String desc, String person, String lat, String longi, String countr, String city, String type, String ano )
    {
        eventID = event;
        descendant = desc;
        personID = person;
        latitude = lat;
        longitude = longi;
        country = countr;
        this.city = city;
        eventType = type;
        year = ano;
    }


    public eResponse(eventModel m){
        eventID = m.getID();
        descendant = m.getDescendant();
        personID = m.getPersonID();
        latitude = m.getLatitude();
        longitude = m.getLongitude();
        country = m.getCountry();
        city = m.getCity();
        eventType = m.getEventType();
        year = m.getYear();
    }
    /**
     * super called if there was an error
     * @param m = message to be displayed.
     */
    public eResponse(String m){super(m);}

    /**
     *prints out the vars
     * @return String with all vars inside
     */

    public String getBody(){
        Gson a = new Gson();
        return a.toJson(this);
    }

}