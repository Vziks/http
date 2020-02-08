package info.vziks.httpserver.controller;

import info.vziks.httpserver.app.StatusCode;
import info.vziks.httpserver.app.TextBody;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Controller;

public class RedirectController extends Controller {

    @Override
    public Response get() {
        Response response = new Response(StatusCode.MOVED_PERMANENTLY.getCode());
        response.addBody(new TextBody("<html><head><meta http-equiv='refresh' content='0 ; url=" + request.getLocation() + "'></head></html>"));
        String location = "http://" + request.getHost() + request.getLocation();
        response.addHeader("Location", location);
        return response;
    }
}
