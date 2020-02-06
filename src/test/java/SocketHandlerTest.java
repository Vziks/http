import info.vziks.httpserver.app.SocketHandler;
import info.vziks.httpserver.http.Request;
import info.vziks.httpserver.http.Response;
import info.vziks.httpserver.interfaces.Callable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class SocketHandlerTest {

    private Socket mockSocket = mock(Socket.class);
    private Callable mockApp = mock(Callable.class);
    private SocketHandler handler = new SocketHandler(mockSocket, mockApp);
    private Response response = mock(Response.class);
    private InputStream input = new ByteArrayInputStream("GET / HTTP/1.1\n".getBytes());
    private OutputStream output = mock(ByteArrayOutputStream.class);

    @Before
    public void initialize() throws IOException {
        when(mockSocket.getInputStream()).thenReturn(input);
        when(mockSocket.getOutputStream()).thenReturn(output);
        when(mockApp.call(any(Request.class))).thenReturn(response);
        when(response.output()).thenReturn("Testing".getBytes());

        final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @Test
    public void initsWithASocket() {
        assertNotNull(handler.socket);
    }

    @Test
    public void getsInputStream() throws IOException {
        handler.run();
        verify(mockSocket).getInputStream();
    }

    @Test
    public void getsOutputStream() throws IOException {
        handler.run();
        verify(mockSocket).getOutputStream();
    }

    @Test
    public void createsRequest() throws IOException {
        Request request = handler.makeRequest(new ByteArrayInputStream("GET / HTTP/1.1\n".getBytes()));
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void callsApp() {
        handler.run();
        verify(mockApp).call(any(Request.class));
    }

    @Test
    public void writesOutput() throws IOException {
        handler.run();
        verify(output).write("Testing".getBytes());
    }

    @Test
    public void flushesOutput() throws IOException {
        handler.run();
        verify(output).flush();
    }

    @Test
    public void closesOutput() throws IOException {
        handler.run();
        verify(output).close();
    }

}

