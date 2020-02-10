package dataAccess;

import model.*;
//import static org.junit.Assert.*;
import org.junit.* ;
import java.sql.*;

import java.util.ArrayList;
import java.util.Set;
import static org.junit.Assert.*;
public class eventDAOTest {

    private eventDAO event;

    @Before
    public void setUp() throws Exception {
        event = new eventDAO();
    }

    @After
    public void tearDown() {
        event.deleteAllEvents();
    }

    @Test
    public void testPostEvent(){
        eventModel m = new eventModel("mitchell", "0w0", "12", "13", "mitchellvania", "Joetown", "party", "2222", "1");
        String ID = m.getID();
        event.postEvent(m);
        eventModel newM = event.getEventByID(ID);
        assertEquals(newM.getPersonID(), "0w0");
    }
    @Test
    public void testDuplicateInsert(){
        eventModel m = new eventModel("mitchell", "0w0", "12", "13", "mitchellvania", "Joetown", "party", "2222", "1");
        eventModel r = new eventModel("mitchell", "0w0", "12", "13", "mitchellvania", "Joetown", "party", "2222", "1");
        m = event.postEvent(m);
        r =event.postEvent(r);
        try {
            assertNotNull("The second attempt is null", r);
            System.out.println("system failed!");
        }
        catch(AssertionError e){System.out.println("success");}
    }
    @Test
    public void testgetByID(){
        eventModel m = new eventModel("mitchell", "0w0", "12", "13", "mitchellvania", "Joetown", "party", "2222", "1");
        eventModel r = new eventModel("mitchell", "0w0", "12", "13", "mitchellvania", "Joetown", "party", "2222", "2");
        eventModel mdesc = new eventModel("mitchell96", "rrr", "12", "13", "mitchellvania", "Joetown", "party", "2222", "3");
        eventModel rdesc = new eventModel("mitchell96", "rrr", "12", "13", "mitchellvania", "Joetown", "party", "2222", "4");
        event.postEvent(m);
        event.postEvent(r);
        event.postEvent(mdesc);
        event.postEvent(rdesc);
        ArrayList<eventModel> setOne = event.getEventByPerson("rrr");
        ArrayList<eventModel> setTwo = event.getEventByPerson("0w0");
        assertEquals(setTwo.size(), setOne.size());
        assertEquals(setOne.get(0).getDescendant(), "mitchell96");

    }



}