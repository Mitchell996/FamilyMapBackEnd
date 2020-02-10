package model;
import request.*;
import service.*;
import dataAccess.*;
import java.util.ArrayList;

/**
 * class userModel
 * creates a user.  with extra information, it can be built from a person class
 */
public class userModel extends personUsersModel{

    /**
     * String userName
     * how the user identifies himself.  it is not his given name.
     */
    public String userName;

    /**
     * String password
     * How the user authenticates himself.  this is not shown to any public function at any time.
     */
    public String password;

    /**
     * String email
     * The users personal email to validate his account or access his information.
     */
    public String email;

    /**
     * String personID
     * every user is associated with a person, and that person contains all the
     * information about the user and his tree and events.
     */
    public String personID;

    /**
     * constructor userModel:
     * builds most of the userData from the personModel p.  The rest are its unique variables
     * @param uName the Username
     * @param pWord the password
     * @param Email the email
     * @param P the personModel
     */
    public userModel(String uName, String pWord, String Email, personModel P){
        super(P);
        userName = new String(uName);
        password = new String(pWord);
        email = new String(Email);
        personID = P.getID();
    }

    public userModel(String uName, String pWord, String Email, String person, personUsersModel p){
        super(p);
        userName = new String(uName);
        password = new String(pWord);
        email = new String(Email);
        personID = person;
    }

    public userModel(registerRequest response){
        super(response.getFirstName(), response.getLastName(), response.getGender());
        userName = response.getUserName();
        password = response.getPassword();
        email = response.getEmail();
        //personID = response.personID;
        personModel p = new personModel(userName, null, null, null, new personUsersModel(response.getFirstName(), response.getLastName(), response.getGender()));
        personDAO dao = new personDAO();
        fillService filler = new fillService();
        ArrayList<String> parentIDs = filler.generatePersonForUser(userName, 4);
        p.setFather(parentIDs.get(0));
        p.setMother(parentIDs.get(1));
        filler.generateEventsForUser(p);
        try{dao.performPostPerson(p);}
        catch(Exception e){}
        personID = p.getID();
    }

    /**
     * void buildFromModel
     * extracts all the info from it's parents and applies it to the userModel.
     * @param person
     */
    private void buildFromModel(personModel person){}

    /**
     * boolean validate
     * checks to see if the username and password match
     * @param userNameToCheck the inputed username
     * @param passwordToCheck the inputed password
     * @return true if both match, false otherwise
     */
    protected boolean validate(String userNameToCheck, String passwordToCheck){
        return (userName.equals(userNameToCheck) && password.equals(passwordToCheck));
    }

    /**
     * string getEmail()
     * @return the String email
     */
    protected String getEmail(){
        return email;
    }

    /**
     * boolean validatePersonID
     * checks to see if the personID given matches
     * @param newID a different personID
     * @return true if the ID's match.
     */
    protected boolean validatePersonID(String newID){
        return personID.equals(newID);
    }






}