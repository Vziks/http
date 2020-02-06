package info.vziks.httpserver.controller;

import info.vziks.httpserver.app.StatusCode;
import info.vziks.httpserver.app.TextBody;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Controller;

public class ParamController extends Controller {

    @Override
    public Response get() {
        StringBuilder body = new StringBuilder();

        for (String key : request.getParams().keySet()) {
            String value = request.getParams().get(key);
            String data = key + " = " + value + " ";
            body.append(data);
        }
        Response response = new Response(StatusCode.OK.getCode());
        response.addBody(new TextBody(body.toString()));
        return response;
    }
}
