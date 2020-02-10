package service;
import request.*;
import response.*;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.authDAO;
import dataAccess.eventDAO;
import dataAccess.personDAO;
import dataAccess.userDAO;

import static org.junit.Assert.*;

public class registerServiceTest {

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
    public void registerDuplicateTest() {
        registerRequest r = new registerRequest("Mitchell96","password", "email", "mitch", "johnson", "m");
        registerService registerer = new registerService();
        try {
            response resp = registerer.register(r);
            response respTwo = registerer.register(r);


            if (respTwo.getMessage() == null) {
                assertFalse(true);
            } else {
                assertTrue(true);
            }
        }
        catch(Exception e){
            assertTrue(true);
        }
    }
    @Test
    public void registerTest(){
        registerRequest r = new registerRequest("Mitchell96","password", "email", "mitch", "johnson", "m");
        registerService registerer = new registerService();
        registerResponse resp = (registerResponse)registerer.register(r);
        userModel m = user.getUser("Mitchell96", "password");
        assertNotNull(m);
    }


    @Test
    public void isUniqueUserName() {
        registerRequest r = new registerRequest("Mitchell96","password", "email", "mitch", "johnson", "m");
        registerService registerer = new registerService();
        try {
            response resp = registerer.register(r);
            assertFalse(true);
        }catch(NullPointerException e){
            assertTrue(true);
        }
        /*if(registerer.isUniqueUserName("Mitchell96", user)){
            assertFalse(true);
        }
        assertTrue(true);*/
    }

}