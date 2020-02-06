package info.vziks.httpserver.app;

import info.vziks.httpserver.interfaces.HTTPBody;

public class TextBody implements HTTPBody {

    private String text;

    public TextBody(String text) {
        this.text = text;
    }

    public byte[] output() {
        return text.getBytes();
    }

    public String contentType() {
        return "text/html";
    }

    public long contentLength() {
        return text.length();
    }

    public String getText() {
        return text;
    }
}
