package response;
import com.google.gson.Gson;

import model.*;
public class personResponse extends response{

    public String descendant;
    public String personID;
    public String firstName;
    public String lastName;
    public String gender;
    public String father;
    public String mother;
    public String spouse;

    /**
     * constructor personResponse
     * puts all the data into a response
     * @param desc descendant
     * @param person personID
     * @param first firstNAme
     * @param last lastName
     * @param gen gender
     * @param father father personID
     * @param mother mother personID
     * @param spouse spouse personID
     */
    public personResponse(String desc, String person, String first, String last, String gen, String father,
                          String mother, String spouse){
        descendant = desc;
        personID = person;
        firstName = first;
        lastName = last;
        gender = gen;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }
    public personResponse(personModel m) {
        descendant = m.getDescendant();
        personID = m.getID();
        firstName = m.getFirstName();
        lastName = m.getLastName();
        gender = m.getGender();
        father = m.getFather();
        mother = m.getMother();
        spouse = m.getSpouse();
    }
    public personResponse(String m){
        super(m);
    }


    /**
     * returns all that good kush in string form
     * @return all the vars in a string
     */
    public String getBody(){
        Gson a = new Gson();
        return a.toJson(this);
    }
}