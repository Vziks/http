package info.vziks.httpserver.app;

import info.vziks.httpserver.utils.RequestLogger;
import org.apache.commons.cli.*;

import java.io.File;

/**
 * Class Arguments
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public class Arguments {

    private Options options = new Options();
    private CommandLine cmd;

    private String[] arguments;
    private Config config;

    public Arguments(String[] args) {
        Option port = new Option("p", "port", true, "port server");
        port.setRequired(false);
        options.addOption(port);

        Option dir = new Option("d", "directory", true, "path to directory");
        dir.setRequired(false);
        options.addOption(dir);

        this.arguments = args;
        this.config = Config.getInstance();
        this.init();
    }

    public void init() {
        this.parse();
        this.port();
        this.dir();
    }

    public void port() {

        if (cmd.getOptionValue("port") != null) {
            try {
                config.setPort(Integer.parseInt(cmd.getOptionValue("port")));
            } catch (NumberFormatException e) {
                RequestLogger.log(RequestLogger.ERROR, e);
                e.printStackTrace();
            }
        }
    }

    public String dir() {

        if (cmd.getOptionValue("directory") != null) {
            File f = new File(cmd.getOptionValue("directory"));
            if (f.exists() && f.isDirectory()) {
                config.setDir(cmd.getOptionValue("directory"));
            }
        }
        return config.getDir();
    }

    public Config getConfig() {
        return config;
    }

    private void parse() {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            cmd = parser.parse(options, arguments);
        } catch (ParseException e) {
            formatter.printHelp("http-server", options);
            RequestLogger.log(RequestLogger.ERROR, e);
            System.exit(1);
        }
    }
}
