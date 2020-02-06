package info.vziks.httpserver.controller;

import info.vziks.httpserver.app.StatusCode;
import info.vziks.httpserver.app.TextBody;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Controller;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;

public class AccessController extends Controller {

    public static final String AUTH = "admin:admin"; // YWRtaW46YWRtaW4=

    @Override
    public Response get() {
        Response response;
        if (this.authenticate()) {
            response = new Response(StatusCode.OK.getCode());
            response.addBody(new TextBody(request.getLogs().toString()));
        } else {
            response = new Response(StatusCode.UNAUTHORIZED.getCode());
            response.addBody(new TextBody(StatusCode.UNAUTHORIZED.getMessage()));
        }
        return response;
    }

    private Boolean authenticate() {
        String auth = request.getAuthorization().get("Key");
        String credentials = null;

        if (auth != null) {
            credentials = new String(DatatypeConverter.parseBase64Binary(auth), StandardCharsets.UTF_8);
        }
        return AUTH.equals(credentials);
    }
}
