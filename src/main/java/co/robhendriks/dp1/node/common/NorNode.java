package co.robhendriks.dp1.node.common;

import co.robhendriks.dp1.circuit.CircuitException;
import co.robhendriks.dp1.node.AbstractNode;
import co.robhendriks.dp1.Keywords;
import co.robhendriks.dp1.node.Input;

@Keywords("NOR")
public class NorNode extends AbstractNode {
    public NorNode(String name) {
        super(name);
    }

    @Override
    public void update(final AbstractNode origin) throws CircuitException {
        boolean valid = true;
        for (AbstractNode node : previous) {
            if (node.getValue() == Input.HIGH) {
                valid = false;
                break;
            }
        }
        value = valid ? Input.HIGH : Input.LOW;
        super.update(origin);
    }
}
