package info.vziks.httpserver.http;

import info.vziks.httpserver.app.StatusCode;
import info.vziks.httpserver.interfaces.HTTPBody;
import info.vziks.httpserver.utils.RequestLogger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Class Response
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public class Response {
    public static String SERVER_NAME = "http-server";

    private boolean isInterpreter = false;

    private int status;
    private Map<String, String> headers;
    private HTTPBody body;

    public Response(int status) {
        this.status = status;
        this.headers = new HashMap<>();
    }

    public void addBody(HTTPBody body) {
        this.body = body;
    }

    public void addHeader(String header, String content) {
        headers.put(header, content);
    }

    public byte[] output() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(this.responseString().getBytes());
            if (body != null) {
                outputStream.write(body.output());
            }
        } catch (IOException e) {
            RequestLogger.log(RequestLogger.ERROR, e);
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    private String responseString() {
        String httpVersion = "HTTP/1.1";
        String statusString = this.buildStatus();
        String headerString = this.buildHeaders();
        return httpVersion + " " + statusString + "\n" +
                headerString + "\n";
    }

    private String buildStatus() {
        return status + " " + StatusCode.getMessageByCode(status);
    }

    private String buildHeaders() {
        this.addHeader("server", SERVER_NAME);
        this.addHeader("Connection", "close");
        this.addHeader("Date", DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.systemDefault())));
        if (body != null) {
            this.addHeader("Content-Type", body.contentType());
            this.addHeader("Content-Length", String.valueOf((isInterpreter ? body.output().length : body.contentLength())));
        }

        StringBuilder builder = new StringBuilder();
        for (String header : headers.keySet()) {
            builder.append(header).append(": ").append(headers.get(header));
            builder.append("\n");
        }
        return builder.toString();
    }

    public Response setInterpreter(boolean interpreter) {
        isInterpreter = interpreter;
        return this;
    }

    public int getStatus() {
        return status;
    }
}
