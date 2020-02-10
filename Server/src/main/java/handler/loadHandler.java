package handler;
import com.google.gson.Gson;
import request.*;
import response.*;
import service.*;
import model.*;
import java.util.ArrayList;
public class loadHandler {

    public response handleLoad(String body)
    {
        Gson stringify = new Gson();
        loadRequest load = stringify.fromJson(body, loadRequest.class);
        return new loadService().load(load);
    }
}
