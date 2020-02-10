package handler;
import request.*;
import response.*;
import service.*;
public class clearHandler {

    public static response handleClear(){
        response resp;
        try {
            clearService toClear = new clearService();
            toClear.clear();
            resp = new response("Success");
        } catch(Exception e) {
            resp = new response("error code: 500 Internal service error");
        }
        return resp;
    }

}
