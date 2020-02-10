package service;
import response.*;
import request.*;
import model.*;
import dataAccess.*;
/**
 * class loginService
 * validates the username and password and checks authToken
 */
public class loginService{

    /**
     * validate
     * takes the username, password, and authToken and checks to make sure they match a row in db
     * @param r the username and password
     * @return sends back data or an error
     */
    public response validate(loginRequest r){
        String username = r.getUsername();
        String password = r.getPassword();
        userDAO userD = new userDAO();
        userModel m = userD.getUser(username, password);
        if(m == null){
            response resp = new response("invalid username or password");
            return resp;
        }
        authDAO authD = new authDAO();
        authTokenModel auth = authD.postAuthToken(m);
        loginResponse resp = new loginResponse(auth.getAuth(), auth.personID, auth.getUserName());
        return resp;
    }

    /**
     * every time a user logs in, a new auth token should be created for them
     * @param m user with its personID attached so it can get a new AuthToken
     * @return true if successful.
     */
    public String generateNewAuth(userModel m){return null;}

}