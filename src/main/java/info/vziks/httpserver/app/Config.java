package info.vziks.httpserver.app;

import info.vziks.httpserver.utils.RequestLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class Config
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public class Config {
    private int port;
    private String dir;
    private int poll;
    private String php;
    private String python;

    private static volatile Config instance;

    public static Config getInstance() {
        Config localInstance = instance;
        if (localInstance == null) {
            synchronized (Config.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Config();
                }
            }
        }
        return localInstance;
    }

    private Config() {
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("http-config.properties")) {

            Properties properties = new Properties();

            properties.load(inputStream);

            this.port = Integer.parseInt(properties.getProperty("server.port"));
            this.dir = System.getProperty(
                    properties.getProperty("server.folder")) == null ?
                    properties.getProperty("server.folder"):
                    System.getProperty(properties.getProperty("server.folder")
                    )  + "/public";
            this.poll = Integer.parseInt(properties.getProperty("server.poll"));
            this.php = properties.getProperty("server.php");
            this.python = properties.getProperty("server.python");

        } catch (IOException e) {
            RequestLogger.log(RequestLogger.ERROR, e);
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public Config setPort(int port) {
        this.port = port;
        return this;
    }

    public String getDir() {
        return dir;
    }

    public Config setDir(String dir) {
        this.dir = dir;
        return this;
    }

    public int getPoll() {
        return poll;
    }

    public String getPhp() {
        return php;
    }

    public String getPython() {
        return python;
    }
}
