package co.robhendriks.dp1.circuit;

import co.robhendriks.dp1.node.AbstractNode;
import co.robhendriks.dp1.node.NodeFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CircuitParser implements Closeable {
    private final CircuitReader reader;
    private final Map<String, AbstractNode> nodes = new LinkedHashMap<>();

    public CircuitParser(final CircuitReader reader) {
        if (reader == null) {
            throw new NullPointerException("reader == null");
        }
        this.reader = reader;
    }

    private void createNodes() throws IOException {
        final NodeFactory factory = NodeFactory.getInstance();

        AbstractNode node;
        for (Map.Entry<String, String> entry : reader.getNodes().entrySet()) {
            node = factory.create(entry.getValue(), entry.getKey());
            if (node == null) {
                throw new IOException("Unable to create node");
            }
            nodes.put(entry.getKey(), node);
        }
    }

    private void linkNodes() throws IOException {
        AbstractNode prev;
        AbstractNode next;
        for (Map.Entry<String, List<String>> entry : reader.getEdges().entrySet()) {
            prev = nodes.get(entry.getKey());
            if (prev == null) {
                continue;
            }

            for (String name : entry.getValue()) {
                next = nodes.get(name);
                if (next == null) {
                    continue;
                }
                prev.next.add(next);
                next.previous.add(prev);
            }
        }
    }

    public Circuit parse() throws IOException {
        nodes.clear();
        reader.read();
        createNodes();
        linkNodes();
        return new Circuit(nodes);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    public Map<String, AbstractNode> getNodes() {
        return nodes;
    }
}
