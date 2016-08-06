package co.robhendriks.dp1.node.common;

import co.robhendriks.dp1.circuit.CircuitException;
import co.robhendriks.dp1.node.AbstractNode;
import co.robhendriks.dp1.Keywords;
import co.robhendriks.dp1.node.Input;

@Keywords("NOT")
public class NotNode extends AbstractNode {
    public NotNode(String name) {
        super(name);
    }

    @Override
    public void update(final AbstractNode origin) throws CircuitException {
        AbstractNode prev = previous();
        if (prev != null) {
            value = (prev.getValue() == Input.LOW) ? Input.HIGH : Input.LOW;
        }
        super.update(origin);
    }
}
