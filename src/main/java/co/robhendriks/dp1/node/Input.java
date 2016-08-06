package co.robhendriks.dp1.node;

public enum Input {
    HIGH(1),
    LOW(0);

    public final int value;

    Input(int value) {
        this.value = value;
    }

    public static Input parse(int value) {
        for (Input input : Input.values()) {
            if (input.value == value) {
                return input;
            }
        }
        return null;
    }

    public static Input fromString(String value) {
        switch (value) {
            default: return LOW;
            case "INPUT_HIGH": return HIGH;
        }
    }
}
