package model;
import response.*;
public class eventModel extends Model {

    private String descendant;
    private String personID;
    private String latitude;
    private String longitude;
    private String country;
    private String eventType;
    private String city;
    private String year;
    private String eventID;

    /**
     * constructor eventModel
     * since there's no good parent class, I have to do it all here.
     * @param DESC = descendant
     * @param person = personID
     * @param lat = latitude
     * @param LONG = longitude
     * @param COUNTRY = country
     * @param cit
     * @param event = eventType
     * @param YEAR = year
     */
    public eventModel( String DESC, String person, String lat, String LONG, String COUNTRY, String cit, String event, String YEAR){
        super();
        descendant = DESC;
        personID = person;
        latitude = lat;
        longitude = LONG;
        country = new String(COUNTRY);
        city = cit;
        eventType = new String(event);
        year = YEAR;
    }
    public eventModel( String DESC, String person, String lat, String LONG, String COUNTRY, String cit, String event, String YEAR, String ID){
        setID(ID);
        descendant = DESC;
        personID = person;
        latitude = lat;
        longitude = LONG;
        country = new String(COUNTRY);
        city = cit;
        eventType = new String(event);
        year = YEAR;
    }
    public eventModel(eResponse event){
        setID(event.eventID);
        descendant = event.descendant;
        personID = event.personID;
        latitude = event.latitude;
        longitude = event.longitude;
        country = event.country;
        city = event.city;
        eventType = event.eventType;
        year    = event.year;
    }
    public void fix(){
         setID(eventID);
    }

    /**
     * a getter
      * @return String descendant
     */
    public String getDescendant(){
        return descendant;
    }

    /**
     * another getter
     * @return String personID
     */
    public String getPersonID(){
        return personID;
    }

    /**
     *gets latitude
     * @return latitude
     */
    public String getLatitude(){
        return latitude;
    }

    /**
     *
     * @return longitude
     */
    public String getLongitude(){
        return longitude;
    }

    /**
     *
     * @return country
     */
    public String getCountry(){
        return country;
    }

    public String getCity(){
        return city;
    }

    /**
     *
     * @return eventType
     */
    public String getEventType(){
        return eventType;
    }

    /**
     * Golly, I wonder what this could do?
     * @return int year
     */
    public String getYear(){
        return year;
    }



}