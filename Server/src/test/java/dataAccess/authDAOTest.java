package dataAccess;

import model.*;
//import static org.junit.Assert.*;
import org.junit.*;
import java.sql.*;

import java.util.ArrayList;
import java.util.Set;
import static org.junit.Assert.*;
public class authDAOTest {

    private authDAO auth;

    @Before
    public void setUp() throws Exception {
        auth = new authDAO();
    }

    @After
    public void tearDown() {
        auth.deleteAuthTokens();
    }

    @Test
    public void testPostPerson() throws Exception
    {
        //authTokenModel a = new authTokenModel("mitchell96", "waffles");
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", "cheese", proto );
        auth.postAuthToken(me);
        authTokenModel m = auth.getUserAuth("cheese", "Mitchell96");
        assertEquals(m.personID, "cheese");
        assertNotNull(m.getAuth());

    }

    @Test
    public void testFaultyAuth() throws Exception{

       try {
           authTokenModel shouldFail = auth.getAuth("wheeeeeeee");
           assertNotNull("it correctly failed!", shouldFail);
       }
       catch(Exception e){
           System.out.println("rejected faulty token!");
       }
       catch(AssertionError e){
           System.out.println("success");
       }
    }

    @Test
    public void getAuthTest() throws Exception{
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", "cheese", proto );
        authTokenModel m = auth.postAuthToken(me);
        assertNotNull(m);
        String authToken = m.getAuth();
        authTokenModel r = auth.getAuth(authToken);
        assertEquals(r.getAuth(), authToken);
    }

}