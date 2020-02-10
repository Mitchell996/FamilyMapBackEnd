package handler;
import request.registerRequest;
import service.registerService;
import response.*;
//import org.json.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class registerHandler {


    public response handleRegister(String body){
        Gson stringify = new Gson();
        registerRequest request = stringify.fromJson(body, registerRequest.class);
        registerService reg = new registerService();
        return reg.register(request);
    }
}
