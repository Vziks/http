package info.vziks.httpserver.interpreter;

import info.vziks.httpserver.interfaces.Interpreter;

import java.io.*;

/**
 * Class PHPInterpreter
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public class PHPInterpreter extends AbstractInterpreter implements Interpreter {
    @Override
    public StringBuilder process(File file) {
        builder.command("php", "-f", file.toString());
        builder.directory(new File(config.getPhp()));
        return this.getBufferOutput();
    }
}
