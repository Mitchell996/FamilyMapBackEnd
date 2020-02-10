package dataAccess;
import model.*;
//import static org.junit.Assert.*;
import org.junit.* ;
import java.sql.*;
import java.util.*;
import java.util.Set;

import static org.junit.Assert.* ;
import service.*;


public class userDAOTest {

    private userDAO user;
    private authDAO auth;

    @Before
    public void setUp() throws Exception {
       user = new userDAO();
       auth = new authDAO();
    }

    @After
    public void tearDown() throws Exception {
        user.deleteUsers();
        auth.deleteAuthTokens();
    }

    @Test
    public void testPostDAO(){
        clearService clearer = new clearService();
        clearer.clear();
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", "00000000", proto );
        user.postUser(me);
        userModel returned = user.getUser("Mitchell96", "cakes");
        assertNotNull(returned);
        assertEquals("00000000", returned.personID);
    }
    @Test
    public void testUsernames(){
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", "00000000", proto );
        user.postUser(me);
        userModel returned = user.getUser("Mitchell96", "cakes");
        authTokenModel authToken = auth.postAuthToken(returned);
        assertEquals(authToken.getUserName(), returned.userName);
    }
    @Test
    public void getUsersTest(){
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        userModel me = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", "00000000", proto );
        user.postUser(me);
        userModel returned = user.getUser("Mitchell96");
        userModel secondGet = user.getUser("Mitchell96", "cakes");
        assertEquals(secondGet.email, returned.email);
    }
    @Test
    public void getUsernamesTest(){
        personUsersModel proto = new personUsersModel("mitchell", "Johnson", "m");
        userModel me1 = new userModel("Mitchell96", "cakes", "mitchell96@gmail.com", "00000000", proto );
        userModel me2 = new userModel("Mitchell9", "cakes", "mitchell96@gmail.com", "00000000", proto );
        userModel me3 = new userModel("Mitchell", "cakes", "mitchell96@gmail.com", "00000000", proto );
        user.postUser(me1);
        user.postUser(me2);
        user.postUser(me3);
        ArrayList<String> usernames = user.getUserNames();
        assertEquals(usernames.size(), 3);
    }


}