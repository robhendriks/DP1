package co.robhendriks.dp1.command;

import java.lang.reflect.Method;

public final class CommandBinding {
    private final Method method;
    private final CommandCollection collection;

    public CommandBinding(final Method method, final CommandCollection collection) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }
        if (collection == null) {
            throw new NullPointerException("collection == null");
        }
        this.method = method;
        this.collection = collection;
    }

    public boolean invoke(String[] args) throws CommandException {
        try {
            return (boolean) method.invoke(collection, new Object[] {args});
        } catch(Exception e) {
            throw new CommandException(e);
        }
    }
}
