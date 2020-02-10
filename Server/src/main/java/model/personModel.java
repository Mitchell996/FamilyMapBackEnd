package model;
import response.*;
/**
 * class personModel
 * establishes the model of a person.
 */
public class personModel extends personUsersModel {
    /**
     * String descendant
     * Holds the username that this person 'descends' from
     * */
    private String descendant;
    /**
     * String father
     * tells you who the father is, if there is one
     */
    private String father;
    /**
     * String mother
     * tells you who their mother is, if there is one given
     */
    private String mother;
    /**
     * String spouse
     * tells you who they're married to, if they are.
     */
    private String spouse;

    private String personID;

    public void fix(){
        setID(personID);
    }

    /**
     * constructor personModel
     * any of these params can be null, except personUserModel
     * @param DESC = descendants
     * @param dad = father
     * @param mom = mother
     * @param wife = spouse
     * @param temp = all the info for the super.
     */
    public personModel(String DESC, String dad, String mom, String wife, personUsersModel temp){
        super(temp);
        descendant = DESC;

        father = dad;

        mother = mom;
        spouse = wife;

    }
    public personModel(String DESC, String dad, String mom, String wife, personUsersModel temp, String ID){
        super(temp);
        descendant = DESC;
        father = dad;
        mother = mom;
        spouse = wife;
        setID(ID);

    }
    public personModel(personResponse m ){
        super(m.firstName, m.lastName, m.gender);
        descendant = m.descendant;
        father = m.father;
        mother = m.mother;
        spouse = m.spouse;
        setID(m.personID);
    }
    public void setMother(String mom){
        mother = mom;
    }
    public void setFather(String dad){
        father = dad;
    }
    public void setSpouse(String wife){
        spouse = wife;
    }
    public void setDescendant(String DESC)
    {
        descendant = DESC;
    }


    /**
     * string getMother
     * @return String mother
     */
    public String getMother(){
        return mother;
    }

    /**
     * String getFather
     * @return String father
     */
    public String getFather(){
        return father;
    }

    /**
     * String getSpouse
     * @return String spouse
     */
    public String getSpouse(){
        return spouse;
    }

    public String getDescendant() {
        return descendant;
    }







}