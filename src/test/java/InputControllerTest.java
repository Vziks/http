import info.vziks.httpserver.controller.InputController;
import info.vziks.httpserver.http.Request;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class InputControllerTest {

    public Request request = mock(Request.class);
    public InputController controller = new InputController();

    @Before
    public void setup() {
        when(request.getRoute()).thenReturn("/input");
        when(request.getParams()).thenReturn(new HashMap<>());
    }

    @Test
    public void createsForm() {
        assertNotNull(controller.form);
    }

    @Test
    public void getsForm() {
        when(request.getMethod()).thenReturn("GET");
        assertEquals(200, controller.send(request).getStatus());
    }

    @Test
    public void displaysData() {
        controller.form.put("input1", "Testing");
        assertThat(controller.paramDisplay("input1"), CoreMatchers.containsString("Testing"));
    }

    @Test
    public void postsForm() {
        when(request.getMethod()).thenReturn("POST");
        request.getParams().put("input1", "testing");
        assertEquals(302, controller.send(request).getStatus());
        assertEquals("testing", controller.form.get("input1"));
    }

    @Test
    public void escapesString() {
        when(request.getMethod()).thenReturn("POST");
        request.getParams().put("input1", "<script>alert('here');</script>");
        controller.send(request);
        assertEquals("&lt;script&gt;alert(&apos;here&apos;);&lt;/script&gt;", controller.form.get("input1"));
    }

    @Test
    public void deletesForm() {
        when(request.getMethod()).thenReturn("POST");
        request.getParams().put("input1", "testing");
        controller.send(request);
        assertEquals("testing", controller.form.get("input1"));
        when(request.getMethod()).thenReturn("DELETE");
        assertEquals(302, controller.send(request).getStatus());
        assertEquals(null, controller.form.get("input1"));
    }

}
