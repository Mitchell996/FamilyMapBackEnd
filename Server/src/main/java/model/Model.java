/**
 * package model
 * contains all parent and child classes of the models that will be used.
 */
package model;
import java.util.UUID;

/**
 * class Model
 *  The parent class of all model classes.  contains only ID, and just some basic functionality
 */
public class Model {

    /**
     * id
     * a randomly generated id number that is necessary for
     * every model
     */
    private String id  = UUID.randomUUID().toString();;
    /**
     * constructor Model:
     * generates a new id for this model.
     */
    public Model(){
         id = UUID.randomUUID().toString();
    }

    /**
     *
     * @return the String contained in id
     */
    public String getID(){
        return id;
    }
    public void setID(String I)
    {
        id = I;
    }

}