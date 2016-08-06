package co.robhendriks.dp1.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringFormatter {
    private static final Pattern PATTERN = Pattern.compile("%([^%]+)%");

    private final Map<String, String> words;

    private StringFormatter(final Map<String, String> words) {
        this.words = words;
    }

    public static StringFormatterBuilder Builder() {
        return new StringFormatterBuilder();
    }

    public String format(String str) {
        if (str == null) {
            return str;
        }
        StringBuffer sb = new StringBuffer();
        Matcher mat = PATTERN.matcher(str);
        String key, value;
        while(mat.find()) {
            key = mat.group(1);
            if ((value = words.get(key)) == null) {
                continue;
            }
            mat.appendReplacement(sb, value);
        }

        mat.appendTail(sb);
        return sb.toString();
    }

    public static class StringFormatterBuilder {
        private final Map<String, String> words = new HashMap<>();

        public StringFormatterBuilder word(String key, String value) {
            words.put(key, value);
            return this;
        }

        public StringFormatter build() {
            return new StringFormatter(words);
        }
    }
}

