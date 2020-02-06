import info.vziks.httpserver.controller.DefaultController;
import info.vziks.httpserver.http.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class DefaultControllerTest {

    public Request request = mock(Request.class);
    public DefaultController controller = new DefaultController();

    @Before
    public void init() {
        when(request.getRoute()).thenReturn("/default");
    }

    @Test
    public void returnsNotAllowed() {
        when(request.getMethod()).thenReturn("GET");
        assertEquals(405, controller.send(request).getStatus());
    }

    @Test
    public void returnsNotAllowedPost() {
        when(request.getMethod()).thenReturn("POST");
        assertEquals(405, controller.send(request).getStatus());
    }

    @Test
    public void returnsNotAllowedPut() {
        when(request.getMethod()).thenReturn("PUT");
        assertEquals(405, controller.send(request).getStatus());
    }

    @Test
    public void returnsNotAllowedDelete() {
        when(request.getMethod()).thenReturn("DELETE");
        assertEquals(405, controller.send(request).getStatus());
    }

    @Test
    public void returnsNotAllowedHead() {
        when(request.getMethod()).thenReturn("HEAD");
        assertEquals(405, controller.send(request).getStatus());
    }

    @Test
    public void returnsOptions() {
        when(request.getMethod()).thenReturn("OPTIONS");
        assertEquals(200, controller.send(request).getStatus());
    }
}
