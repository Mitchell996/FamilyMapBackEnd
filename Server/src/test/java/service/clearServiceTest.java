package service;
import model.*;
import dataAccess.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class clearServiceTest {

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

    }

    @Test
    public void clearTest() {
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p = new personModel("Mitchell96", "Derek", "diana", null, proto);
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", p );
        authTokenModel toUse = auth.postAuthToken(me);
        //System.out.println("the auth given " + toUse.getAuth());
        eventModel m = new eventModel("Mitchell96", "0w0", "12", "13", "mitchellvania", "Joetown", "party", "2222");
        String ID = m.getID();
        m = event.postEvent(m);
        person.performPostPerson(p);
        clearService clearer = new clearService();
        clearer.clear();
        m = event.getEventByID(ID);
        assertNull(m);
    }
    @Test
    public void clearEmptyTest(){
        try{
            clearService clearer = new clearService();
            clearer.clear();
        } catch(Exception e){
            assertFalse(true);
        }
        assertTrue(true);
    }
}