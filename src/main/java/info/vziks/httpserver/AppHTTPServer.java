package info.vziks.httpserver;


import info.vziks.httpserver.app.Arguments;
import info.vziks.httpserver.app.HTTPServer;
import info.vziks.httpserver.app.RequestHandler;
import info.vziks.httpserver.utils.RequestLogger;

import java.io.IOException;
import java.util.Objects;

/**
 * Class AppHTTPServer
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public class AppHTTPServer {

    public static void main(String[] args) {
        Arguments arguments = new Arguments(args);

        RequestHandler requestHandler = new RequestHandler(arguments.dir());

        HTTPServer httpServer = null;
        try {
            httpServer = new HTTPServer(arguments.getConfig(), new RequestLogger(requestHandler));
        } catch (IOException e) {
            RequestLogger.log(RequestLogger.ERROR, e);
            e.printStackTrace();
        }
        Objects.requireNonNull(httpServer).start();
    }
}
