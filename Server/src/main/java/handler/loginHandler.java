package handler;
import service.*;
import request.*;
import response.*;
import com.google.gson.Gson;


public class loginHandler {

    public response handleLogin(String body) {
        Gson stringify = new Gson();
        loginRequest request = stringify.fromJson(body, loginRequest.class);
        loginService login = new loginService();
        return login.validate(request);
    }

}
