import info.vziks.httpserver.controller.AccessController;
import info.vziks.httpserver.http.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class AccessControllerTest {

    public Request request = mock(Request.class);
    public AccessController controller = new AccessController();

    @Before
    public void setup() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRoute()).thenReturn("/access");
        when(request.getLogs()).thenReturn(new LinkedList<>());
        when(request.getAuthorization()).thenReturn(new HashMap<>());
    }

    @Test
    public void returnsUnauthorized() {
        request.getAuthorization().put("Key", "wrong:password");
        assertEquals(401, controller.send(request).getStatus());
    }

    @Test
    public void returnsLogs() {
        request.getAuthorization().put("Key", "YWRtaW46YWRtaW4=\n");
        assertEquals(200, controller.send(request).getStatus());
    }
}
