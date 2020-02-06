package info.vziks.httpserver.controller;

import info.vziks.httpserver.app.StatusCode;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Controller;

public class OptionsController extends Controller {

    @Override
    public Response get() {
        Response response = new Response(StatusCode.OK.getCode());
        response.addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        return response;
    }

    @Override
    public Response post() {
        return get();
    }

    @Override
    public Response put() {
        return get();
    }

    @Override
    public Response head() {
        return get();
    }

    @Override
    public Response options() {
        return get();
    }
}
