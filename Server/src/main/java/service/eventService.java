package service;
import java.lang.reflect.Array;
import java.util.ArrayList;

import response.*;
import dataAccess.*;
import model.*;
public class eventService{

    /**
     * gets the specific event at eventID
     *
     * @return a response on if it was successful or not.
     */
    /*public response getAllEvents(String personID)
    {
        eventDAO event = new eventDAO();
        ArrayList<eventModel> events = event.getAllEvents();
        eventsResponse respond = new eventsResponse(events);
        return respond;
    }*/

    public response getEventByID(String eventID, String authToken)
    {
        eventDAO event = new eventDAO();
        eventModel events = event.getEventByID(eventID);
        authDAO auth = new authDAO();
        authTokenModel modelAuth = auth.getAuth(authToken);
        personDAO persondao = new personDAO();
        ArrayList<personModel> people = persondao.getDescendants(modelAuth.getUserName());
        if(events == null || !(events.getDescendant().equals(modelAuth.getUserName())))
        {
            response resp = new response("Sorry, no person exists with that ID that you have access to");
            return resp;
        }
        //for(personModel person : people){
           // if(person.getID().equals(events.))
        //}

        eResponse toRespond = new eResponse(events);
        /*CREATE ERROR RESPONSE HERE*/


        //ArrayList<eventModel> e = new ArrayList<>();
        //e.add(events);
        //eventsResponse respond = new eventsResponse(e);
        return toRespond;
    }

    /**
     * gets all events related to the current authToken
     * @return all the events related to the authToken given
     */
    public response getEvents(String authToken)
    {
        personDAO p = new personDAO();
        ArrayList<personModel> persons = p.performGetPersons(authToken);
        ArrayList<eventModel> allEvents = new ArrayList<>();
        for( personModel person : persons){
            eventDAO event = new eventDAO();
            ArrayList<eventModel> events = event.getEventByPerson(person.getID());
            allEvents.addAll(events);
        }
       // authDAO a = new authDAO();
        //authTokenModel auth = a.getAuth(authToken);
        //eventDAO event = new eventDAO();
        //ArrayList<eventModel> events = event.getEventByPerson(auth.personID);
        eventsResponse respond = new eventsResponse(allEvents);
        return respond;
    }

    /*public response createEvent( String personID ) {

    return null;

    }*/




}