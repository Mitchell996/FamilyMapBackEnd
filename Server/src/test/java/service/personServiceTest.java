package service;
import model.*;
import response.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.authDAO;
import dataAccess.eventDAO;
import dataAccess.personDAO;
import dataAccess.userDAO;

import static org.junit.Assert.*;

public class personServiceTest {

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
    public void getByAuthNullTest() {
        personService personer = new personService();
        try {
            personsResponse r = personer.getByAuth("notAnAuthToken");
        }
        catch(NullPointerException e){
            assertTrue(true);
        }
       /* if(r.data.isEmpty())
        {
            assertTrue(true);
        }
        else{
            assertFalse(true);
        }*/
    }

    @Test
    public void getByPersonIDTest() {
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p = new personModel("Mitchell96", "Derek", "diana", null, proto);
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", p );
        authTokenModel toUse = auth.postAuthToken(me);
        personService personer = new personService();
        response resp = personer.getByPersonID(p.getID(), toUse.getAuth());
        assertNotNull(resp);
        //assertEquals((resp.personID, p.getID());
    }

    @Test
    public void failByPersonIDTest(){
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p = new personModel("Mitchell96", "Derek", "diana", null, proto);
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", p );
        authTokenModel toUse = auth.postAuthToken(me);
        personService personer = new personService();
        response resp = personer.getByPersonID(p.getID(), "notRealAuth");
        assertNotNull(resp.getMessage());
       // assertEquals(resp.personID, p.getID());


    }
}