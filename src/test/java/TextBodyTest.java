import info.vziks.httpserver.app.TextBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class TextBodyTest {

    private TextBody body = new TextBody("<html><h1>Not Found</h1></html>");

    @Test
    public void initsWithText() {
        assertEquals("<html><h1>Not Found</h1></html>", body.getText());
    }

    @Test
    public void returnsContentType() {
        assertEquals("text/html", body.contentType());
    }

    @Test
    public void returnsContentLength() {
        assertEquals(31, body.contentLength());
    }

    @Test
    public void returnsByteOutput() {
        assertEquals(byte[].class, body.output().getClass());
        assertEquals(31, body.output().length);
    }
}
