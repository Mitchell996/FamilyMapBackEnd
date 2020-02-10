package response;
import com.google.gson.Gson;

import java.util.ArrayList;
import model.*;
public class personsResponse extends response {


    public ArrayList<personResponse> data;

    /**
     * constructor personsResponse
     * collects a bunch of person responses into one to send back.
     */
    public personsResponse(ArrayList<personResponse> newPersons) {
        data = newPersons;
    }

    public personsResponse(ArrayList<personModel> newPersons, boolean worthless) {
        data = new ArrayList<>();
        for (personModel person : newPersons) {
            data.add(new personResponse(person));
        }
    }

    /**
     * returns everything.
     *
     * @return String with all the vars
     */
    public String getBody() {
        Gson a = new Gson();
        return a.toJson(this);

    }
}