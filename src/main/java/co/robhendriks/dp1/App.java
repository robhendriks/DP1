package co.robhendriks.dp1;

import co.robhendriks.dp1.circuit.Circuit;
import co.robhendriks.dp1.circuit.CircuitParser;
import co.robhendriks.dp1.circuit.CircuitReader;
import co.robhendriks.dp1.command.CommandManager;
import co.robhendriks.dp1.command.DefaultCommandCollection;
import co.robhendriks.dp1.util.StringUtils;
import org.apache.commons.cli.CommandLine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class App {
    private final CommandLine line;
    private final CommandManager commandManager;

    private Circuit circuit;
    private boolean running = false;

    public App(final CommandLine line) {
        if (line == null) {
            throw new NullPointerException("line == null");
        }
        this.line = line;
        commandManager = new CommandManager(this);
        commandManager.register(new DefaultCommandCollection());
    }

    private void loop() {
        if (running) {
            throw new IllegalStateException("Already running");
        }
        running = true;

        Scanner scanner = new Scanner(System.in);
        String line;
        while (running) {
            line = scanner.nextLine();
            commandManager.execute(line.split("\\s"));
        }
        System.out.println("Goodbye");
    }

    public void run() throws IOException {
        if (!line.hasOption("file")) {
            System.out.println("No file specified");
            System.exit(1);
        }

        String filename = line.getOptionValue("file");
        System.out.println(String.format("Loading \"%s\"...", filename));

        try {
            InputStream in = new FileInputStream(filename);

            CircuitReader cr = new CircuitReader(in);
            CircuitParser cp = new CircuitParser(cr);

            circuit = cp.parse();
            cp.close();

            int count = circuit.getNodes().size();
            System.out.println(String.format("Loaded %d %s", count, StringUtils.pluralize(count, "node", "nodes")));
        } catch(Exception e) {
            throw new IOException("Unable to load circuit", e);
        }

        loop();
    }

    public boolean requestShutdown() {
        if (running) {
            running = false;
            return true;
        }
        return false;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public boolean isRunning() {
        return running;
    }
}
