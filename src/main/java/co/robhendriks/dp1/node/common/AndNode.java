package co.robhendriks.dp1.node.common;

import co.robhendriks.dp1.circuit.CircuitException;
import co.robhendriks.dp1.node.AbstractNode;
import co.robhendriks.dp1.Keywords;
import co.robhendriks.dp1.node.Input;

@Keywords("AND")
public class AndNode extends AbstractNode {
    public AndNode(String name) {
        super(name);
    }

    @Override
    public void update(final AbstractNode origin) throws CircuitException {
        boolean valid = true;
        for (AbstractNode node : previous) {
            if (node.getValue() == Input.LOW) {
                valid = false;
                break;
            }
        }
        value = valid ? Input.HIGH : Input.LOW;
        super.update(origin);
    }
}
