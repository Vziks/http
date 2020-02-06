import info.vziks.httpserver.controller.DirectoryController;
import info.vziks.httpserver.http.Request;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class DirectoryControllerTest {

    public Request request = mock(Request.class);

    public DirectoryController controller = new DirectoryController();

    @Before
    public void setup() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRoute()).thenReturn("/");
        when(request.getBaseDirectory()).thenReturn("public");
    }

    @Test
    public void returnsSuccess() {
        assertEquals(200, controller.send(request).getStatus());
    }

    @Test
    public void returnsContent() {
        controller.send(request);
        assertThat(controller.buildDirectoryContents(), CoreMatchers.containsString("<a href='info.py'>info.py</a>"));
    }
}
