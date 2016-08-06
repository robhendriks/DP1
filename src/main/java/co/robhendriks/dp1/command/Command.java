package co.robhendriks.dp1.command;

import co.robhendriks.dp1.util.StringFormatter;

public final class Command {
    private final String name;
    private final String usage;
    private final CommandBinding binding;
    private final StringFormatter formatter;

    public Command(final String name, final String usage, final CommandBinding binding) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        if (binding == null) {
            throw new NullPointerException("binding == null");
        }
        this.name = name;
        this.usage = usage;
        this.binding = binding;

        formatter = StringFormatter.Builder()
                .word("cmd", name)
                .build();
    }

    public void execute(String[] args) throws CommandException {
        if (!binding.invoke(args)) {
            System.out.println("Usage: " + formatter.format(usage));
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
