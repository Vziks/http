package info.vziks.httpserver.app;

import info.vziks.httpserver.http.Request;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Callable;
import info.vziks.httpserver.utils.RequestLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketHandler implements Runnable {

    public Socket socket;
    public Callable app;

    public SocketHandler(Socket socket, Callable app) {
        this.socket = socket;
        this.app = app;
    }

    public void run() {
        try (InputStream input = socket.getInputStream();
             OutputStream output = socket.getOutputStream()) {
            Request request = this.makeRequest(input);
            Response response = app.call(request);
            output.write(response.output());
            output.flush();
        } catch (IOException e) {
            RequestLogger.log(RequestLogger.ERROR, e);
            e.printStackTrace();
        }
    }

    public Request makeRequest(InputStream input) {
        return new Request(input);
    }
}
