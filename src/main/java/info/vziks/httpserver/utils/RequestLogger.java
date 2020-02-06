package info.vziks.httpserver.utils;

import info.vziks.httpserver.http.Request;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Callable;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class RequestLogger implements Callable {

    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";

    public Callable app;

    public RequestLogger(Callable app) {
        this.app = app;
    }

    public Response call(Request request) {
        log(request, INFO, null);
        return app.call(request);
    }

    static public void log(Request request, String name, Throwable throwable) {
        String message = request.getLog();

        Logger logger = LogManager.getLogger(name);

        switch (name.toLowerCase()) {
            case ("warn"):
                logger.warn(message, throwable);
                break;
            case ("error"):
                logger.error(message, throwable);
                break;
            default:
                logger.info(message, throwable);
                break;
        }
    }

    static public void log(String name, Throwable throwable) {

        Logger logger = LogManager.getLogger(name);

        switch (name.toLowerCase()) {
            case ("warn"):
                logger.warn(throwable.getMessage(), throwable);
                break;
            case ("error"):
                logger.error(throwable.getMessage(), throwable);
                break;
            default:
                logger.info(throwable.getMessage(), throwable);
                break;
        }
    }

}
