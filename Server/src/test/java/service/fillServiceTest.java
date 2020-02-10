package service;
import model.*;
import dataAccess.*;
//import static org.junit.Assert.*;
import org.junit.*;
import java.sql.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import static org.junit.Assert.*;;

public class fillServiceTest {

    personDAO person;
    eventDAO event;
    userDAO user;
    authDAO auth;

    @Before
    public void setUp() throws Exception {
        person = new personDAO();
        event = new eventDAO();
        user = new userDAO();
        auth = new authDAO();
    }

    @After
    public void tearDown() {

        person.performDeleteAllPersons();
        event.deleteAllEvents();
        user.deleteUsers();
        auth.deleteAuthTokens();
    }

    @Test
    public void fillServiceTest() {
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", "00000000", proto);
        user.postUser(me);
        authTokenModel authToken = auth.postAuthToken(me);
        //he has been filled once
        fillService filler = new fillService();
        filler.generatePerson("Mitchell96", 4);
        //filled again
        //make sure that user's person is connected to him and his family
        personModel myPerson = person.performGetPerson(me.personID, authToken.getAuth());
        assertNotNull(myPerson);
        assertNotNull(myPerson.getFather());
        personModel myDad = person.performGetPerson(myPerson.getFather(), authToken.getAuth());
        assertNotNull(myDad);
        assertEquals(myDad.getSpouse(), myPerson.getMother());

    }

    @Test
    public void testRefillAndUser() {
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", "00000000", proto);
        user.postUser(me);
        fillService filler = new fillService();
        filler.generatePerson("Mitchell96", 0);
        ArrayList<personModel> persons = person.getDescendants("Mitchell96");
        assertNotNull(persons.get(0));
    }

}