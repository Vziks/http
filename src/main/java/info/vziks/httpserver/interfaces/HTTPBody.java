package info.vziks.httpserver.interfaces;

/**
 * Class HTTPBody
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public interface HTTPBody {
    byte[] output();
    String contentType();
    long contentLength();
}
