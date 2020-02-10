package dataAccess;

import model.*;
//import static org.junit.Assert.*;
import org.junit.* ;
import java.sql.*;

import java.util.ArrayList;
import java.util.Set;
import static org.junit.Assert.*;

public class personDAOTest {

    private personDAO person;
    private authDAO auth;
    private eventDAO event;

    @Before
    public void setUp() throws Exception {
        person = new personDAO();
        auth = new authDAO();
        event = new eventDAO();
    }

    @After
    public void tearDown() {
        person.performDeleteAllPersons();
        auth.deleteAuthTokens();
        event.deleteAllEvents();
    }

    @Test
    public void testPostPerson() throws Exception
    {
        personUsersModel pu = new personUsersModel("Mitchell", "Johnson", "m");
        personModel p = new personModel("money", "Derek", "diana", null, pu);
        person.performPostPerson(p);
        ArrayList<personModel> all = person.getDescendants("money");
        personModel newP = all.get(0);
        assertEquals("money",newP.getDescendant());
        assertNull(newP.getSpouse());
        assertEquals(newP.getID(), p.getID());
    }

    @Test
    public void testExpandedDatabase(){
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p = new personModel("Mitchell96", "Derek", "diana", null, proto);
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", p );
       authTokenModel toUse = auth.postAuthToken(me);
       //System.out.println("the auth given " + toUse.getAuth());
        eventModel m = new eventModel("Mitchell96", p.getID(), "12", "13", "mitchellvania", "Joetown", "party", "2222");
        String ID = m.getID();
        m = event.postEvent(m);
        person.performPostPerson(p);
        personModel newP = person.performGetPerson(p.getID(), toUse.getAuth() );
        assertEquals(newP.getFather(), "Derek");
        assertEquals(newP.getID(), m.getPersonID());

    }
    @Test
    public void testFaultyAuthToken(){
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p = new personModel("Mitchell96", "Derek", "diana", null, proto);
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", p );
        authTokenModel toUse = auth.postAuthToken(me);
        eventModel m = new eventModel("Mitchell96", "0w0", "12", "13", "mitchellvania", "Joetown", "party", "2222");

        String ID = m.getID();
        m = event.postEvent(m);
        person.performPostPerson(p);
        //try {
            personModel newP = person.performGetPerson(p.getID(), "0");
            assertNull(newP);
        //}
        //catch(Exception e){}
    }

    @Test
    public void getDescendantsTest(){
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p1 = new personModel("Mitchell96", "Derek", "diana", null, proto);
        personUsersModel proto2 = new personUsersModel("Jack", "Johnson", "m");
        personModel p2 = new personModel("Mitchell96", "Derek", "diana", null, proto2);
        personUsersModel proto3 = new personUsersModel("Robert", "Johnson", "m");
        personModel p3 = new personModel("Mitchell", "Derek", "diana", null, proto3);

        person.performPostPerson(p1);
        person.performPostPerson(p2);
        person.performPostPerson(p3);

        ArrayList<personModel> persons = person.getDescendants("Mitchell96");
        assertNotNull(persons);
        assertEquals(persons.size(), 2);
    }

    @Test
    public void getDescendantsFailTest(){
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        personModel p1 = new personModel("Mitchell96", "Derek", "diana", null, proto);
        personUsersModel proto2 = new personUsersModel("Jack", "Johnson", "m");
        personModel p2 = new personModel("Mitchell96", "Derek", "diana", null, proto2);
        personUsersModel proto3 = new personUsersModel("Robert", "Johnson", "m");
        personModel p3 = new personModel("Mitchell", "Derek", "diana", null, proto3);

        person.performPostPerson(p1);
        person.performPostPerson(p2);
        person.performPostPerson(p3);

        ArrayList<personModel> persons = person.getDescendants("Mitch");
        assertTrue(persons.isEmpty());
        //assertEquals(persons.size(), 2);
    }


}