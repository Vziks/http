import info.vziks.httpserver.controller.OptionsController;
import info.vziks.httpserver.http.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class OptionsControllerTest {

    public Request request = mock(Request.class);

    public OptionsController controller = new OptionsController();

    @Before
    public void setup() {
        when(request.getRoute()).thenReturn("/method_options");
    }

    @Test
    public void returnOptions() {
        when(request.getMethod()).thenReturn("GET");
        assertEquals(200, controller.send(request).getStatus());
    }

    @Test
    public void returnOptionsPost() {
        when(request.getMethod()).thenReturn("POST");
        assertEquals(200, controller.send(request).getStatus());
    }

    @Test
    public void returnOptionsPut() {
        when(request.getMethod()).thenReturn("PUT");
        assertEquals(200, controller.send(request).getStatus());
    }

    @Test
    public void returnOptionsHead() {
        when(request.getMethod()).thenReturn("HEAD");
        assertEquals(200, controller.send(request).getStatus());
    }
}
