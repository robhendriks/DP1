package co.robhendriks.dp1.circuit;

import co.robhendriks.dp1.node.AbstractNode;
import co.robhendriks.dp1.node.common.InputNode;
import co.robhendriks.dp1.node.common.OutputNode;

import java.util.HashMap;
import java.util.Map;

public class Circuit {
    private final Map<String, AbstractNode> nodes;
    private final Map<String, InputNode> inputNodes = new HashMap<>();
    private final Map<String, OutputNode> outputNodes = new HashMap<>();

    public Circuit(final Map<String, AbstractNode> nodes) {
        if (nodes == null) {
            throw new NullPointerException("nodes == null");
        }
        this.nodes = nodes;
        init();
    }

    private void init() {
        for (AbstractNode node : nodes.values()) {
            if (node instanceof InputNode) {
                inputNodes.put(node.getName(), (InputNode) node);
            } else if (node instanceof OutputNode) {
                outputNodes.put(node.getName(), (OutputNode) node);
            }
        }
    }

    private void reset() {
        for (InputNode node : inputNodes.values()) {
            node.totalDelay = 0;
        }
        for (OutputNode node : outputNodes.values()) {
            node.origins.clear();
        }
    }

    private void examine() throws Exception {
        reset();
        for (InputNode node : inputNodes.values()) {
            node.update(node);
        }

        String message = "";
        message += "====== RESULT ======\n";
        for (OutputNode node : outputNodes.values()) {
            message += String.format("\t%s: %s\n", node.getName(), node.getValue());
            for (AbstractNode origin : node.origins) {
                if (origin.totalDelay > 0) {
                    message += String.format("\t\t<- %s (%dns)\n", origin.getName(), origin.totalDelay);
                }
            }
        }
        message += "====================\n";
        System.out.println(message);
    }

    public void run() throws CircuitException {
        try {
            examine();
        } catch(Exception e) {
            throw new CircuitException(e);
        }
    }

    public Map<String, AbstractNode> getNodes() {
        return nodes;
    }

    public AbstractNode getNode(String name) {
        return nodes.get(name);
    }

    public Map<String, InputNode> getInputNodes() {
        return inputNodes;
    }

    public InputNode getInputNode(String name) {
        return inputNodes.get(name);
    }

    public Map<String, OutputNode> getOutputNodes() {
        return outputNodes;
    }

    public OutputNode getOutputNode(String name) {
        return outputNodes.get(name);
    }
}
