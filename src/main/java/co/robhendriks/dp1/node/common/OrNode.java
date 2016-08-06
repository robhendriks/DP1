package co.robhendriks.dp1.node.common;

import co.robhendriks.dp1.circuit.CircuitException;
import co.robhendriks.dp1.node.AbstractNode;
import co.robhendriks.dp1.Keywords;
import co.robhendriks.dp1.node.Input;

@Keywords("OR")
public class OrNode extends AbstractNode {
    public OrNode(String name) {
        super(name);
    }

    @Override
    public void update(final AbstractNode origin) throws CircuitException {
        boolean valid = false;
        for (AbstractNode node : previous) {
            if (node.getValue() == Input.HIGH) {
                valid = true;
                break;
            }
        }
        value = valid ? Input.HIGH : Input.LOW;
        super.update(origin);
    }
}
