package handler;
import service.*;
import response.*;
public class personHandler {

    public response handlePerson(String url, String authToken)
    {
        response resp = null;
        String[] urlParts = url.split("/");
        personService ps = new personService();
        if(urlParts.length> 2) {
            String id = urlParts[2];
            resp = ps.getByPersonID(id, authToken);
        }
        else {
            resp = ps.getByAuth(authToken);
        }
        return resp;
    }


}
