import info.vziks.httpserver.app.Config;
import info.vziks.httpserver.app.HTTPServer;
import info.vziks.httpserver.interfaces.Callable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.*;

/**
 * Class HttpServerTest
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
@RunWith(JUnit4.class)
public class HttpServerTest {

    private Config config;

    @Before
    public void executedBeforeEach() {
        config = Config.getInstance();
    }

    @Test
    public void initsWithAPort() throws IOException {
        HTTPServer server = new HTTPServer(config, mock(Callable.class));
        server.serverSocket.close();
        assertEquals(server.config.getPort(), 8000);
    }

    @Test
    public void initsWithApp() throws IOException {
        HTTPServer server = new HTTPServer(config, mock(Callable.class));
        server.serverSocket.close();
        assertNotNull(server.app);
    }

    @Test
    public void createsServerSocket() throws IOException {
        HTTPServer server = new HTTPServer(config, mock(Callable.class));
        server.serverSocket.close();
        assertEquals(server.serverSocket.getClass(), ServerSocket.class);
    }

    @Test
    public void createsSocketHandler() throws IOException {
        HTTPServer server = new HTTPServer(config, mock(Callable.class));
        server.serverSocket.close();
        assertNotNull(server.makeSocketHandler(new Socket()));
    }

    @Test
    public void acceptsOnSocket() throws IOException {
        HTTPServer spyServer = spy(new HTTPServer(config, mock(Callable.class)));
        spyServer.serverSocket.close();
        ServerSocket mockServerSocket = mock(ServerSocket.class);
        spyServer.serverSocket = mockServerSocket;
        Socket mockSocket = mock(Socket.class);
        Thread mockThread = mock(Thread.class);
        when(mockServerSocket.accept()).thenReturn(mockSocket);
        when(mockServerSocket.isClosed()).thenReturn(false, true);
        when(spyServer.makeServerSocket()).thenReturn(mockServerSocket);
        when(spyServer.newThread(mockSocket)).thenReturn(mockThread);

        spyServer.start();
        verify(mockServerSocket).accept();
        verify(mockThread).run();
    }
}
