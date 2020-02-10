/**
 * contains all the parent and children classes
 */
package model;
/**
 * class personUsersModel
 *  sets up some of the similar variables that exist between the persons and Users
 *  implements Model.java
 * */
public class personUsersModel extends Model{
    /**
     * private String firstName:
     * holds their first name.
     */
    private String firstName;
    /**
     * private String lastName:
     * holds their last name.
     */
    private String lastName;
    /**
     * private char Gender:
     * it just holds a char representing their gender, brah
     */
    private String gender;

    /**
     * constructor personUsersModel:
     * calls constructor, and sets all of the class variables
     * @param G Gender
     * @param lName lastname
     * @param fName firstname
     *
     */
    public personUsersModel(String fName, String lName, String G){
        super();
        firstName = new String(fName);
        lastName = new String(lName);
        gender = new String(G);
    }
    public personUsersModel(String fName, String lName, String G, String ID){
        super();
        firstName = new String(fName);
        lastName = new String(lName);
        gender = new String(G);
        setID(ID);
    }

    /**
     * this is just here so I don't have to make anything and the compiler won't get mad.
     */
    public personUsersModel(){}

    /**
     * constructor personUsersModel
     * for convenience and child classes, there's a constructor that can be passed in using a personUsersModel
     * @param p takes all the data from that personModel to build this one
     */
    public personUsersModel(personUsersModel p){
        super();
        firstName = new String(p.getFirstName());
        lastName = new String(p.getLastName());
        gender = new String(p.getGender());

    }

    /**
     * getFirstName()
     * @return  a String containing the person's first name
     */
    public String getFirstName(){return firstName;}

    /**
     * public getLastName()
     * @return a String containing the person's last name
     */
    public String getLastName(){return lastName;}

    /**
     * public getGender()
     * @return  a String containing the persons gender
     */
    public String getGender(){return gender;}
}