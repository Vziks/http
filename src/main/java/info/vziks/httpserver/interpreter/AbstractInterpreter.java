package info.vziks.httpserver.interpreter;

import info.vziks.httpserver.app.Config;
import info.vziks.httpserver.interfaces.Interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Abstract Class AbstractInterpreter
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
abstract public class AbstractInterpreter implements Interpreter {

    StringBuilder stringBuilder;
    protected Config config = Config.getInstance();
    ProcessBuilder builder;

    public AbstractInterpreter() {
        this.builder = new ProcessBuilder();
        this.stringBuilder = new StringBuilder();
    }

    public StringBuilder getBufferOutput() {
        builder.redirectErrorStream(true);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(process).getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n\t");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Objects.requireNonNull(process).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return stringBuilder;
    }

}
