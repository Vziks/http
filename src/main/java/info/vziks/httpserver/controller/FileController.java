package info.vziks.httpserver.controller;

import info.vziks.httpserver.app.StatusCode;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Controller;

public class FileController extends Controller {

    @Override
    public Response get() {
        Response response = new Response(StatusCode.OK.getCode());
        if (request.getRange().isEmpty()) {
            FileBody body = new FileBody(request.getBaseDirectory() + request.getRoute());
            response.setInterpreter(true);
            response.addBody(body);
        } else {
            response = partialResponse();
        }
        return response;
    }

    private Response partialResponse() {
        Response response = new Response(StatusCode.PARTIAL_CONTENT.getCode());
        int start = request.getRange().get("Start");
        int stop = request.getRange().get("Stop");
        FileBody body = new FileBody(request.getBaseDirectory() + request.getRoute());
        body.setRange(start, stop);
        response.addBody(body);
        response.addHeader("Content-Range", "bytes " + start + "-" + stop + "/" + body.file.length());
        return response;
    }
}
