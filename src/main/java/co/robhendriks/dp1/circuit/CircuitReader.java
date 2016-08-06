package co.robhendriks.dp1.circuit;

import co.robhendriks.dp1.node.NodeFactory;
import co.robhendriks.dp1.util.StringUtils;

import java.io.*;
import java.util.*;

public final class CircuitReader implements Closeable {
    private static final Set<String> KEYWORDS = NodeFactory.getInstance().getKeywords();

    private final BufferedReader br;
    private final Map<String, String> nodes = new LinkedHashMap<>();
    private final Map<String, List<String>> edges = new LinkedHashMap<>();

    public CircuitReader(final InputStream in) {
        if (in == null) {
            throw new NullPointerException("in == null");
        }
        br = new BufferedReader(new InputStreamReader(in));
    }

    private void addNode(String key, String[] values) {
        nodes.put(key, values[0]);
    }

    private void addEdge(String key, String[] values) throws IOException {
        List<String> list;
        if ((list = edges.get(key)) == null) {
            list = new LinkedList<>();
            edges.put(key, list);
        }
        for (String value : values) {
            if (!nodes.containsKey(value)) {
                throw new IOException("Invalid reference: " + value);
            }
            list.add(value);
        }
    }

    private void element(String node, String[] edges) throws IOException {
        for (String edge : edges) {
            if (KEYWORDS.contains(edge)) {
                addNode(node, edges);
                return;
            }
        }
        addEdge(node, edges);
    }

    public void read() throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("#")) {
                continue;
            }
            int pos;
            if ((pos = line.indexOf(';')) != -1) {
                line = line.substring(0, pos);
            }
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = StringUtils.trimAll(line.split(":"));
            if (parts.length != 2) {
                continue;
            }

            String node = parts[0];
            String[] edges = StringUtils.trimAll(parts[1].split(","));
            if (edges.length > 0) {
                element(node, edges);
            }
        }
    }

    public Map<String, String> getNodes() {
        return nodes;
    }

    public Map<String, List<String>> getEdges() {
        return edges;
    }

    @Override
    public void close() throws IOException {
        br.close();
    }
}
