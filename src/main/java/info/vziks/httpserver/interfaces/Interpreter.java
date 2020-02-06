package info.vziks.httpserver.interfaces;

import java.io.File;

/**
 * Class Interpreter
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public interface Interpreter {
    StringBuilder process(File file);
}
