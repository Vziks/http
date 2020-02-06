package info.vziks.httpserver.controller;

import info.vziks.httpserver.app.StatusCode;
import info.vziks.httpserver.app.TextBody;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Controller;

import java.util.HashMap;
import java.util.Map;

public class FormController extends Controller {

    public Map<String, String> form;

    public FormController() {
        this.form = new HashMap<>();
    }

    @Override
    public Response get() {
        StringBuilder body = new StringBuilder();
        for(String key : form.keySet()) {
            String data = key + " = " + form.get(key);
            body.append(data);
        }
        Response response = new Response(StatusCode.OK.getCode());
        response.addBody(new TextBody(body.toString()));
        return response;
    }
    @Override
    public Response post() {
        Map<String, String> params = request.getParams();
        String value = params.get("data");
        form.put("data", value);
        return new Response(StatusCode.OK.getCode());
    }

    @Override
    public Response put() {
        return post();
    }

    @Override
    public Response delete() {
        form.remove("data");
        return new Response(StatusCode.OK.getCode());
    }
}
