package co.robhendriks.dp1.util;

import co.robhendriks.dp1.node.AbstractNode;

public class NodeUtils {
    public static String toString(AbstractNode node) {
        if (node == null) {
            throw new NullPointerException("node == null");
        }

        String[] prev = new String[node.previous.size()];
        String[] next = new String[node.next.size()];

        int i = 0;
        for (AbstractNode prevNode : node.previous) {
            prev[i++] = prevNode.getName();
        }
        i = 0;
        for (AbstractNode nextNode : node.next) {
            next[i++] = nextNode.getName();
        }

        return String.format("%s{name=%s, value=%s}", node.getClass().getSimpleName(),
                node.getName(), node.getValue());
    }
}
