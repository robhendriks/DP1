package co.robhendriks.dp1.node.common;

import co.robhendriks.dp1.circuit.CircuitException;
import co.robhendriks.dp1.node.AbstractNode;
import co.robhendriks.dp1.Keywords;
import co.robhendriks.dp1.node.Input;

@Keywords({"INPUT_HIGH", "INPUT_LOW"})
public class InputNode extends AbstractNode {
    public InputNode(String name) {
        super(name);
    }

    @Override
    public void initWithValue(String value) {
        this.value = Input.fromString(value);
    }

    @Override
    public void update(final AbstractNode origin) throws CircuitException {
        super.update(origin);
    }

    @Override
    public int getDelay() {
        return 0;
    }
}
