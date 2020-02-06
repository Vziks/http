package info.vziks.httpserver.app;

import java.util.Arrays;

/**
 * Class StatusCode
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public enum StatusCode {

    OK(200, "OK"),
    CREATED(201, "Created"),
    PARTIAL_CONTENT(206, "Partial Content"),
    MOVED_PERMANENTLY(301, "Found"),
    FOUND(302, "Moved Permanently"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");

    int code;
    String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessageByCode(int code) {
        return Arrays
                .stream(StatusCode.values())
                .filter(e -> e.code == code)
                .findAny()
                .<IllegalArgumentException>orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Unsupported type %s.", code));
                })
                .getMessage();
    }

    public static int getCodeByMessage(String message) {
        return Arrays
                .stream(StatusCode.values())
                .filter(e -> e.message.equals(message))
                .findAny()
                .<IllegalArgumentException>orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Unsupported type %s.", message));
                })
                .getCode();
    }
}
