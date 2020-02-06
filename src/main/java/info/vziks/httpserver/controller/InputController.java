package info.vziks.httpserver.controller;

import info.vziks.httpserver.app.StatusCode;
import info.vziks.httpserver.app.TextBody;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Controller;
import info.vziks.httpserver.utils.HTMLEncoder;

import java.util.HashMap;
import java.util.Map;

public class InputController extends Controller {

    public static String FORM = "<html><h1>Use the form!!!</h1>" +
            "<form action='/input' method='POST'>" +
            "<input name='input1' type='text' placeholder='Input 1'><br>" +
            "<input name='input2' type='text' placeholder='Input 2'><br>" +
            "<input name='input3' type='text' placeholder='Input 3'><br>" +
            "<input type='submit' value='Submit'>" +
            "</form>";

    public Map<String, String> form;
    public Map<String, String> params;

    public InputController() {
        this.form = new HashMap<>();
    }

    public String paramDisplay(String key) {
        return "<h2>" + key + ": " + form.get(key) + "</h2>";
    }

    @Override
    public Response get() {
        StringBuilder body;
        body = new StringBuilder();
        body.append(FORM);
        body.append(paramDisplay("input1"));
        body.append(paramDisplay("input2"));
        body.append(paramDisplay("input3"));
        body.append("</html>");
        Response response = new Response(StatusCode.OK.getCode());

        response.addBody(new TextBody(body.toString()));
        return response;
    }

    public void saveParam(String key) {
        String value = params.get(key);
        if (value != null) {
            form.put(key, HTMLEncoder.encode(value));
        }
    }

    @Override
    public Response post() {
        this.params = request.getParams();
        this.saveParam("input1");
        this.saveParam("input2");
        this.saveParam("input3");
        Response response = new Response(StatusCode.FOUND.getCode());
        response.addHeader("Location", "/input");
        return response;
    }

    @Override
    public Response put() {
        return post();
    }

    @Override
    public Response delete() {
        form.clear();
        Response response = new Response(StatusCode.FOUND.getCode());
        response.addHeader("Location", "/input");
        return response;
    }
}
