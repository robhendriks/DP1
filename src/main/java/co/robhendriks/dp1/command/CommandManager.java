package co.robhendriks.dp1.command;

import co.robhendriks.dp1.App;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class CommandManager {
    private final App app;
    private final Map<String, Command> commands = new HashMap<>();

    public CommandManager(final App app) {
        if (app == null) {
            throw new NullPointerException("app == null");
        }
        this.app = app;
    }

    public void execute(String[] args) {
        String name = (args.length > 0) ? args[0].toLowerCase() : null;
        if (name == null || name.isEmpty()) {
            System.out.println("Unknown command. Type \"help\" for help.");
            return;
        }

        Command command;
        if ((command = commands.get(name)) == null) {
            System.out.println("Unknown command \"" + name + "\". Type \"help\" for help");
            return;
        }

        String[] newArgs = new String[0];
        if (args.length > 1) {
            newArgs = Arrays.copyOfRange(args, 1, args.length);
        }

        try {
            command.execute(newArgs);
        } catch(CommandException e) {
            System.out.println("Error while executing command \"" + name + "\".");
            e.printStackTrace();
        }
    }

    public <T extends CommandCollection> void register(T collection) {
        if (collection == null) {
            throw new NullPointerException("collection == null");
        }
        Class clazz = collection.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            CommandHandler handler;
            if ((handler = method.getAnnotation(CommandHandler.class)) == null || handler.name() == null || handler.name().isEmpty()) {
                continue;
            }

            String name = handler.name().toLowerCase();
            String usage = handler.usage();
            commands.put(name, new Command(name, usage, new CommandBinding(method, collection)));
        }
        collection.init(app);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
