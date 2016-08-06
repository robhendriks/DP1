package co.robhendriks.dp1.node.common;

import co.robhendriks.dp1.circuit.CircuitException;
import co.robhendriks.dp1.node.AbstractNode;
import co.robhendriks.dp1.Keywords;
import co.robhendriks.dp1.node.Input;

@Keywords("XOR")
public class XorNode extends AbstractNode {
    public XorNode(String name) {
        super(name);
    }

    @Override
    public void update(final AbstractNode origin) throws CircuitException {
        if (previous.size() != 2) {
            throw new CircuitException("XOR expects 2 inputs");
        }
        AbstractNode a = previous.get(0);
        AbstractNode b = previous.get(1);
        value = (a.getValue() != b.getValue()) ? Input.HIGH : Input.LOW;
        super.update(origin);
    }
}
