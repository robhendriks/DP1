package co.robhendriks.dp1.util;

public final class StringUtils {
    public static String[] trimAll(String... args) {
        if (args == null) {
            throw new NullPointerException("args == null");
        }
        String str;
        for (int i = 0; i < args.length; i++) {
            str = args[i];
            args[i] = (str != null) ? str.trim() : null;
        }
        return args;
    }

    public static String pluralize(int value, String singular, String plural) {
        return (value == 0 || value > 1) ? plural : singular;
    }
}
