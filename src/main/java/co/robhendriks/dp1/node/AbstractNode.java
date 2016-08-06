package co.robhendriks.dp1.node;

import co.robhendriks.dp1.circuit.CircuitException;
import co.robhendriks.dp1.util.NodeUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode {
    public final List<AbstractNode> previous = new ArrayList<>();
    public final List<AbstractNode> next = new ArrayList<>();

    protected Input value = Input.LOW;

    private final String name;
    public int totalDelay = 0;

    public AbstractNode(final String name) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        this.name = name;
    }

    public AbstractNode init(String str) {
        initWithValue(str);
        return this;
    }

    public void initWithValue(String value) {
    }

    public void update(final AbstractNode origin) throws CircuitException {
        for (AbstractNode node : next) {
            node.update(origin);
            origin.totalDelay += node.getDelay();
        }
    }

    public String getName() {
        return name;
    }

    public Input getValue() {
        return value;
    }

    public void setValue(Input value) {
        Input oldValue = this.value;
        if (value != oldValue) {
            this.value = value;
            System.out.println(String.format("Changing value of \"%s\" from \"%s\" to \"%s\".", name, oldValue, value));
        } else {
            System.out.println("Value hasn't changed.");
        }
    }

    public AbstractNode previous() {
        return (previous.size() > 0) ? previous.get(0) : null;
    }

    public AbstractNode next() {
        return (next.size() > 0) ? next.get(0) : null;
    }

    public int getDelay() {
        return 15;
    }

    @Override
    public String toString() {
        return NodeUtils.toString(this);
    }
}
