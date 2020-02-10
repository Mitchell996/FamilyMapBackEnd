package service;
import response.*;
import request.*;
import model.*;
import dataAccess.*;
import java.util.ArrayList;
/**
 * class registerService
 * just registers a user, and does whatever
 */
public class registerService{

    /**
     * RegisterResult register
     * @param r = an http request with data from the handler
     * @return the result, in registerResult form
     */
    public response register(registerRequest r)
    {
        //String gender = r.gender;
        //String firstName = r.firstName;
        //String lastName = r.lastName;
        //personUsersModel p = new personUsersModel(firstName, lastName, gender);
        //personModel person = new personModel(r.username,null, null,null, p);

        userDAO dao = new userDAO();
        authDAO authD = new authDAO();
        if(isUniqueUserName(r.getUserName(), dao)) {
            userModel m = new userModel(r);
            if(m.getGender() == null || m.userName == null ||m.personID==null||m.password==null||m.email==null||m.getFirstName()==null||m.getLastName()==null)
            {
                response resp = new response("invalid input");
                return resp;
            }
            if(m.getGender().length() > 1)
            {
                response resp = new response("Gender information is too long");
                return resp;
            }
            userModel user = dao.postUser(m);
            /*if(user == null){
                response resp = new response("use already exists");
                return resp;
            }*/
            authTokenModel auth = authD.postAuthToken(m);
            registerResponse resp = new registerResponse(auth.getAuth(), auth.personID, auth.getUserName());
            return resp;
        }
        else{
            response resp = new response("Username already taken");
            return resp;
        }
    }

    /**
     * boolean isUniqueUserName
     * checks if the username is unique
     * @param  userName presented
     * @return if the username is already in the db.
     */
    public boolean isUniqueUserName(String userName, userDAO dao){
        //userDAO dao = new userDAO();
        ArrayList<String> usernames = dao.getUserNames();
        for(String user : usernames)
        {
            if(userName.equals(user))
                return false;
        }
        return true;
    }

    /**
     * void createAuthToken
     * creates an authToken for the user.
     */
    //public void createAuthToken(){}

    /**
     * getUsers just returns all the rows in the database as usermodels
     * @return an arraylist of all the usermodels in the db
     */
   /* public ArrayList<userModel> getUsers(){
        userDAO dao = new userDAO();
        return dao.getAllUsers();
    }*/

}