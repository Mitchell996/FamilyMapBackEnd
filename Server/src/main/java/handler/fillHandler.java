package handler;
import request.*;
import service.*;
import response.*;
import com.google.gson.Gson;

public class fillHandler {

    public response handleFill(String body){
        Gson stringify = new Gson();
        fillRequest fill = stringify.fromJson(body, fillRequest.class);
       fillService service = new fillService();
        service.generatePerson(fill);
        response resp = new response("success!");
        return resp;

    }
    public response handleFill(String username, int numGenerations)
    {
        fillService service = new fillService();
        response r = service.generatePerson(username, numGenerations);
        return r;
    }

}
