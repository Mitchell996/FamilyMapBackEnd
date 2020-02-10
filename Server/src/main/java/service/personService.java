package service;
import response.*;
import dataAccess.*;
import model.*;
import java.util.ArrayList;
public class personService{

    /**
     * gets the person associated with this personID
     * @return response with data or error message
     */
    /*public personsResponse getAllPersons(){
        personDAO dao = new personDAO();
        ArrayList<personModel> persons = dao.getAllPersons();
        personsResponse resp = new personsResponse(persons, true);
        return resp;
    }*/
    public personsResponse getByAuth(String authID)
    {
        authDAO auth = new authDAO();
        authTokenModel m = auth.getAuth(authID);
        personDAO dao = new personDAO();
        ArrayList<personModel> persons = dao.getDescendants(m.getUserName());
        personsResponse resp = new personsResponse(persons, true);
        return resp;
    }
    public response getByPersonID(String personID, String authID)
    {
            personDAO dao = new personDAO();
            personModel p = dao.performGetPerson(personID, authID);
            if(p== null)
            {
                response resp = new response("Sorry, no person exists with that ID that you have access to");
                return resp;
            }
            personResponse resp = new personResponse(p);
            return resp;
    }





    /**
     * gets all persons/descendants associated with this authToken
     * @return response with data  or error message
     */
   /* public response getPersons(){


        return null;
    }*/






}