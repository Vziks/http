import info.vziks.httpserver.app.StatusCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Class StatusCodeTest
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
@RunWith(JUnit4.class)
public class StatusCodeTest {

    @Test
    public void checkOKStatus() {
        assertEquals("OK", StatusCode.getMessageByCode(200));
        assertEquals(200, StatusCode.getCodeByMessage("OK"));
    }

    @Test
    public void checkFoundStatus() {
        assertEquals("Found", StatusCode.getMessageByCode(301));
        assertEquals(301, StatusCode.getCodeByMessage("Found"));
    }

    @Test
    public void checkCreatedStatus() {
        assertEquals("Created", StatusCode.getMessageByCode(201));
        assertEquals(201, StatusCode.getCodeByMessage("Created"));
    }

    @Test
    public void checkEmunStatus() {
        assertEquals("Not Found", StatusCode.NOT_FOUND.getMessage());
        assertEquals(404, StatusCode.NOT_FOUND.getCode());
    }
}
