package service;
import java.util.ArrayList;
import model.*;
import response.*;
import request.*;
import dataAccess.*;
public class loadService{

    /**
     * clears all data, then sends all the information to be created neatly.
     * @param r the data on events, users, and people
     * @return message on if it failed or not.
     */
    public response load(loadRequest r){

        /*usersRequest usersR = r.users;
        personsResponse personsR = r.people;
        eventsResponse eventsR = r.events;
        ArrayList<registerRequest> users = usersR.users;
        ArrayList<personResponse> persons = personsR.persons;
        ArrayList<eResponse> events = eventsR.events;
        //ArrayList<userModel> addUsers = new ArrayList<>();
        //ArrayList<personModel> addPersons = new ArrayList<>();
        //ArrayList<eventModel> addEvents = new ArrayList<>();
        int countUsers = 0;
        int countpersons = 0;
        int countEvents = 0;


        for( registerRequest register : users )
        {
            userpost.postUser(new userModel(register));
            countUsers++;
        }
        for(personResponse person : persons ){
                //personpost.performPostPerson(new personModel(person));
                countpersons++;
        }
        for(eResponse event : events ){
            eventpost.postEvent(new eventModel(event));
            countEvents++;
        }*/
        userDAO userpost = new userDAO();
        personDAO personpost = new personDAO();
        eventDAO eventpost = new eventDAO();
        authDAO authpost = new authDAO();
        userModel[] users = r.getUsers();
        personModel[] persons = r.getPersons();
        eventModel[] event =r.getEvents();
        for(int i = 0; i < users.length; i++)
        {
            authpost.postAuthToken(users[i]);
            userModel m = userpost.postUser(users[i]);
            if(m == null)
            {
                response resp = new response("invalid input in users, nothing added in clear");
            }
        }
        for(int i = 0; i < r.getPersons().length; i++){
            persons[i].fix();
            personpost.performPostPerson(persons[i]);
        }
        for(int i = 0; i < event.length; i++)
        {
            event[i].fix();
            eventpost.postEvent(event[i]);
        }

        response resp = new response("successfully added " + users.length + " users, " + persons.length + " persons, and " + event.length + " events" );

        return resp;
    }







}