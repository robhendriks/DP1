package co.robhendriks.dp1.circuit;

public class CircuitException extends Exception {
    public CircuitException(String message) {
        super(message);
    }

    public CircuitException(String message, Throwable cause) {
        super(message, cause);
    }

    public CircuitException(Throwable cause) {
        super(cause);
    }
}
