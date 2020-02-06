package info.vziks.httpserver.interfaces;

import info.vziks.httpserver.app.StatusCode;
import info.vziks.httpserver.app.TextBody;
import info.vziks.httpserver.http.Request;
import info.vziks.httpserver.http.Response;

/**
 * Abstract Class Controller
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
abstract public class Controller {

    public Request request;

    public Response send(Request request) {
        this.request = request;
        Response response = methodNotAllowedResponse();
        switch (request.getMethod()) {
            case "GET":
                response = get();
                break;
            case "POST":
                response = post();
                break;
            case "PUT":
                response = put();
                break;
            case "OPTIONS":
                response = options();
                break;
            case "HEAD":
                response = head();
                break;
            case "DELETE":
                response = delete();
                break;
        }
        return response;
    }

    public Response get() {
        return methodNotAllowedResponse();
    }

    public Response post() {
        return methodNotAllowedResponse();
    }

    public Response put() {
        return methodNotAllowedResponse();
    }

    public Response head() {
        return methodNotAllowedResponse();
    }

    public Response delete() {
        return methodNotAllowedResponse();
    }

    public Response options() {
        Response response  = new Response(StatusCode.OK.getCode());
        response.addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        return response;
    }

    public Response methodNotAllowedResponse() {
        Response response  = new Response(StatusCode.METHOD_NOT_ALLOWED.getCode());
        response.addBody(new TextBody(StatusCode.METHOD_NOT_ALLOWED.getMessage()));
        return response;
    }
}
