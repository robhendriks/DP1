package co.robhendriks.dp1.node.common;

import co.robhendriks.dp1.node.AbstractNode;
import co.robhendriks.dp1.Keywords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Keywords("PROBE")
public class OutputNode extends AbstractNode {
    public final List<AbstractNode> origins = new ArrayList<>();

    public OutputNode(String name) {
        super(name);
    }

    @Override
    public void update(final AbstractNode origin) {
        if (!origins.contains(origin)) {
            origins.add(origin);
        }

        if (previous.size() > 0) {
            AbstractNode node = previous.get(0);
            value = node.getValue();
        }
    }
}
