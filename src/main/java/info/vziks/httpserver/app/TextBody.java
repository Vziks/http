package info.vziks.httpserver.app;

import info.vziks.httpserver.controller.FileBody;
import info.vziks.httpserver.interfaces.HTTPBody;

public class TextBody implements HTTPBody {

    private String text;

    public TextBody(String text) {
        this.text = text;
    }

    public byte[] output() {
        return FileBody.gzipCompress(text.getBytes());
    }

    public String contentType() {
        return "text/html";
    }

    public long contentLength() {
        return output().length;
    }

    public String getText() {
        return text;
    }
}
