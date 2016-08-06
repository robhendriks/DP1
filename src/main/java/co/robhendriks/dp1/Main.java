package co.robhendriks.dp1;

import org.apache.commons.cli.*;

public class Main {
    private final CommandLineParser parser = new DefaultParser();
    private final HelpFormatter formatter = new HelpFormatter();

    private CommandLine line;

    public Main() {
        // TODO
    }

    public void run(String[] args) {
        Options options = new Options();

        options.addOption(Option.builder("h")
                .longOpt("help")
                .build());

        options.addOption(Option.builder("f")
                .hasArg()
                .longOpt("file")
                .build());

        try {
            line = parser.parse(options, args);
            if (line.hasOption("help")) {
                formatter.printHelp("dp1", options);
            } else {
                new App(line).run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main().run(args);
    }
}
