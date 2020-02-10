package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import response.*;
import dataAccess.authDAO;
import dataAccess.eventDAO;
import dataAccess.personDAO;
import dataAccess.userDAO;
import model.authTokenModel;
import model.eventModel;
import model.personModel;
import model.personUsersModel;
import model.userModel;

import static org.junit.Assert.*;

public class eventServiceTest {

    private personDAO person;
    private userDAO user;
    private authDAO auth;
    private eventDAO event;

    @Before
    public void setUp(){
        person = new personDAO();
        user = new userDAO();
        auth = new authDAO();
        event = new eventDAO();

    }

    @After
    public void tearDown(){
        clearService clearer = new clearService();
        clearer.clear();
    }

    @Test
    public void getEventByID() {
        eventService eventer = new eventService();
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p = new personModel("Mitchell96", "Derek", "diana", null, proto);
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", p );
        authTokenModel toUse = auth.postAuthToken(me);
        eventModel m = new eventModel("Mitchell96", "0w0", "12", "13", "mitchellvania", "Joetown", "party", "2222", "1");
        event.postEvent(m);
        eventModel r = new eventModel("Mitchell96", "111", "12", "13", "mitchellvania", "Joetown", "party", "2222", "2");
        event.postEvent(r);
        eventModel newM = new eventModel((eResponse)(eventer.getEventByID("1", toUse.getAuth())));
        eventModel newR = new eventModel((eResponse)(eventer.getEventByID("2", toUse.getAuth())));
        assertEquals(m.getPersonID(), newM.getPersonID());
        assertEquals(r.getID(), newR.getID());
    }

    @Test
    public void getEvents() {
        eventService eventer = new eventService();
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p = new personModel("Mitchell96", "Derek", "diana", null, proto);
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", p );
        authTokenModel toUse = auth.postAuthToken(me);
        eventModel m = new eventModel("Mitchell96", "0w0", "12", "13", "mitchellvania", "Joetown", "party", "2222", "1");
        event.postEvent(m);
        eventModel r = new eventModel("Mitchell96", "111", "12", "13", "mitchellvania", "Joetown", "party", "2222", "2");
        event.postEvent(r);
        eventsResponse resp = (eventsResponse)eventer.getEvents(toUse.getAuth());
        assertNull(resp.getMessage());


    }
}