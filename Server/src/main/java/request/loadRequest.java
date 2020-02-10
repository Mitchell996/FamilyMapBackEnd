package request;
import model.eventModel;
import model.personModel;
import model.userModel;
import response.*;
public class loadRequest{

     userModel[] users;
     eventModel[] events;
     personModel[] persons;

    /**
     * loads up all the data that we need to send the appropriate request to load
     * @param use
     * @param event
     * @param people
     */
    public loadRequest(userModel[] use, eventModel[] event, personModel[] people)
    {
        users = use;
        events = event;
        persons = people;

    }

    public userModel[] getUsers() {
        return users;
    }

    public eventModel[] getEvents() {
        return events;
    }

    public personModel[] getPersons() {
        return persons;
    }

    /**
     * will organize and open all the data from the responses and requests
     * @return
     */
    public String getBody(){return null;
        //String body = user.getBody() + "\n events:" + events.getBody() + "\n persons:" + people.getBody();
        //return body;
    }





}