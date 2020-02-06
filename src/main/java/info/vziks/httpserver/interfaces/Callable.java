package info.vziks.httpserver.interfaces;

import info.vziks.httpserver.http.Request;
import info.vziks.httpserver.http.Response;

/**
 * Class Callable
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public interface Callable {
    Response call(Request request);
}
