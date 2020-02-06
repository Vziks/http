package info.vziks.httpserver.interpreter;

import info.vziks.httpserver.interfaces.Interpreter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class PythonInterpreter
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public class PythonInterpreter extends AbstractInterpreter implements Interpreter {

    private byte[] content;

    @Override
    public StringBuilder process(File file) {
        Path path = Paths.get(file.toString());
        try {
            content = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        builder.command("python", "-c", new String(content));
        builder.directory(new File(config.getPython()));
        return this.getBufferOutput();
    }
}
