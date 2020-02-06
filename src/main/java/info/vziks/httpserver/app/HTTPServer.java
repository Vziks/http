package info.vziks.httpserver.app;

import info.vziks.httpserver.interfaces.Callable;
import info.vziks.httpserver.utils.RequestLogger;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class HTTPServer
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public class HTTPServer {

    public Config config;
    public Callable app;
    public ServerSocket serverSocket;

    public HTTPServer(Config config, Callable app) throws IOException {
        this.config = config;
        this.app = app;
        this.serverSocket = this.makeServerSocket();
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(config.getPoll());

        System.out.printf("Http Server started at %s\n" +
                "Listening on http://127.0.0.1:%d\n" +
                "Document root is %s\n" +
                "Press Ctrl-C to quit.\n", java.util.Calendar.getInstance().getTime(), config.getPort(), config.getDir());

        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                executor.execute(this.newThread(socket));
            } catch (IOException e) {
                RequestLogger.log(RequestLogger.ERROR, e);
                e.printStackTrace();
                break;
            }
        }
    }

    public ServerSocket makeServerSocket() throws IOException {

        ServerSocket localServerSocket;
        while (true) {
            try {
                localServerSocket = new ServerSocket(config.getPort());
                break;
            } catch (BindException e) {
                config.setPort(config.getPort() + 1);
                continue;
            }
        }
        return localServerSocket;
    }

    public SocketHandler makeSocketHandler(Socket socket) {
        return new SocketHandler(socket, app);
    }

    public Runnable newThread(Socket socket) {
        return new Thread(this.makeSocketHandler(socket));
    }
}
