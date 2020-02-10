package service;
import response.response;
import dataAccess.*;
/**
 * class clearService
 * as far as I understand this just clears everything
 */
public class clearService{

    /**
     * void clear
     * clears all the databases and whatnot.
     */
    public response clear()
    {
        authDAO auth = new authDAO();
        auth.deleteAuthTokens();
        eventDAO event = new eventDAO();
        event.deleteAllEvents();
        personDAO person = new personDAO();
        person.performDeleteAllPersons();
        userDAO user = new userDAO();
        user.deleteUsers();
        response r = new response("success");
        return r;
    }
}