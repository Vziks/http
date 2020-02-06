import info.vziks.httpserver.controller.ParamController;
import info.vziks.httpserver.http.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ParamControllerTest {

    public Request request = mock(Request.class);
    public ParamController controller = new ParamController();

    @Test
    public void returnsParams() {
        when(request.getRoute()).thenReturn("/parameters");
        when(request.getMethod()).thenReturn("GET");
        when(request.getParams()).thenReturn(new HashMap<>());
        request.getParams().put("data", "test");
        assertEquals(200, controller.send(request).getStatus());
    }
}
