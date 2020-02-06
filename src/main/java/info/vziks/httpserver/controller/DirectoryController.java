package info.vziks.httpserver.controller;

import info.vziks.httpserver.app.StatusCode;
import info.vziks.httpserver.app.TextBody;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DirectoryController extends Controller {

    @Override
    public Response get() {
        Response response = new Response(StatusCode.OK.getCode());
        response.addBody(new TextBody(this.buildDirectoryContents()));
        return response;
    }

    public String buildDirectoryContents() {
        File directory = new File(request.getBaseDirectory() + request.getRoute());
        ArrayList<String> names = new ArrayList<>(Arrays.asList(Objects.requireNonNull(directory.list())));
        StringBuilder builder = new StringBuilder();
        builder.append("<html><h1>").append(request.getBaseDirectory()).append(request.getRoute()).append("</h1><ul>");
        for (String name : names) {
            if (request.getRoute().equals("/")) {
                builder.append("<li><a href='").append(name).append("'>").append(name).append("</a></li>");
            } else {
                builder.append("<li><a href='").append(request.getRoute()).append("/").append(name).append("'>").append(name).append("</a></li>");
            }
        }
        builder.append("</ul></html>");
        return builder.toString();
    }
}
