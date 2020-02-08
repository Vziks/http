import info.vziks.httpserver.controller.FileController;
import info.vziks.httpserver.http.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FileControllerTest {

    public Request request = mock(Request.class);
    public FileController controller = new FileController();

    @Test
    public void returnsFullFile() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRoute()).thenReturn("/test.txt");
        when(request.getRange()).thenReturn(new HashMap<>());
        when(request.getBaseDirectory()).thenReturn("public");
        assertEquals(200, controller.send(request).getStatus());
    }

    @Test
    public void returnsPartiaFile() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRoute()).thenReturn("/test.txt");
        when(request.getRange()).thenReturn(new HashMap<>());
        HashMap<String, Integer> rangeMap = new HashMap<>();
        when(request.getBaseDirectory()).thenReturn("public");

        rangeMap.put("Length", 10);
        rangeMap.put("Start", 0);
        rangeMap.put("Stop", 9);
        request.getRange().putAll(rangeMap);
        assertEquals(206, controller.send(request).getStatus());
    }
}
