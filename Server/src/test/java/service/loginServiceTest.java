package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import request.*;
import response.*;
import dataAccess.authDAO;
import dataAccess.eventDAO;
import dataAccess.personDAO;
import dataAccess.userDAO;
import model.personModel;
import model.personUsersModel;
import model.userModel;

import static org.junit.Assert.*;

public class loginServiceTest {


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
    public void validateFalseTest() {
        loginService loginer = new loginService();
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p = new personModel("Mitchell96", "Derek", "diana", null, proto);
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", p );
            user.postUser(me);
            loginRequest r = new loginRequest("Mitchell96", "potato");
            response newResponse = loginer.validate(r);
            loginResponse logins = new loginResponse("cheese!");
            if(newResponse.getClass() == logins.getClass()){
                assertFalse(true);
            }
            else{
                assertTrue(true);
            }


    }
    @Test
    public void validateTrueTest() {
        loginService loginer = new loginService();
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p = new personModel("Mitchell96", "Derek", "diana", null, proto);
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", p );
        user.postUser(me);
        loginRequest r = new loginRequest("Mitchell96", "cakes");
        response newResponse = loginer.validate(r);
        loginResponse logins = new loginResponse("cheese!");
        if(newResponse.getClass() == logins.getClass()){
            assertTrue(true);
        }
        else{
            assertFalse(true);
        }


    }

    //@Test
    //public void generateNewAuth() {


    //}
}