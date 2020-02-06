package info.vziks.httpserver.app;

import info.vziks.httpserver.controller.*;
import info.vziks.httpserver.http.Request;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Callable;
import info.vziks.httpserver.interfaces.Controller;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Class RequestHandler
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public class RequestHandler implements Callable {

    public String baseDirectory;
    public Map<String, Controller> routes;
    public Request request;
    public LinkedList<String> logs;

    public RequestHandler(String directory) {
        this.baseDirectory = directory;
        this.logs = new LinkedList<>();
        this.routes = new HashMap<>();
        drawRoutes();
    }

    public void drawRoutes() {
        routes.put("/default", new DefaultController());
        routes.put("/method_options", new OptionsController());
        routes.put("/redirect", new RedirectController());
        routes.put("/parameters", new ParamController());
        routes.put("directory", new DirectoryController());
        routes.put("/access", new AccessController());
        routes.put("/form", new FormController());

        routes.put("/input", new InputController());
        routes.put("file", new FileController());
    }

    public Response call(Request request) {
        this.request = request;
        request.setBaseDirectory(baseDirectory);
        this.addLog();
        Controller controller = this.getController();
        if (controller == null) {
            return this.notFound();
        } else {
            return controller.send(request);
        }
    }

    private Response notFound() {
        Response response = new Response(StatusCode.NOT_FOUND.getCode());
        response.addBody(new TextBody(StatusCode.NOT_FOUND.getMessage()));
        return response;
    }

    private void addLog() {
        String entry = request.getLog();
        if (logs.size() == 5) {
            logs.removeFirst();
        }
        logs.addLast(entry);
        request.setLogs(logs);
    }

    private Controller getController() {
        String route = this.filterRoute();
        return routes.get(route);
    }

    private String filterRoute() {
        String route = request.getRoute();
        if (this.isDirectory(baseDirectory + route)) {
            return "directory";
        } else if (this.isFile(baseDirectory + route)) {
            return "file";
        } else {
            return route;
        }
    }

    private Boolean isFile(String path) {
        return new File(path).exists();
    }

    private Boolean isDirectory(String path) {
        return new File(path).isDirectory();
    }
}
