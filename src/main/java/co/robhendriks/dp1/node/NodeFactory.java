package co.robhendriks.dp1.node;

import co.robhendriks.dp1.Keywords;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class NodeFactory {
    private static final NodeFactory INSTANCE = new NodeFactory();

    private final Reflections reflections = new Reflections("co.robhendriks");
    private final Map<String, Class<? extends AbstractNode>> types = new HashMap<>();
    private final Set<String> keywords;

    private NodeFactory() {
        Set<Class<? extends AbstractNode>> subTypes = reflections.getSubTypesOf(AbstractNode.class);
        for (Class<? extends AbstractNode> subType : subTypes) {
            Keywords keywords = subType.getAnnotation(Keywords.class);
            if (keywords == null || keywords.value() == null) {
                continue;
            }
            for (String key : keywords.value()) {
                if (key != null && !key.isEmpty()) {
                    types.put(key, subType);
                }
            }
        }
        keywords = types.keySet();
    }

    public AbstractNode create(String key, String value) {
        if (key == null) {
            throw new NullPointerException("str == null");
        }
        Class<? extends AbstractNode> clazz = types.get(key);
        if (clazz == null) {
            return null;
        }

        Class[] params = {String.class};
        try {
            return clazz.getConstructor(params)
                    .newInstance((value != null) ? value : key)
                    .init(key);
        } catch (Exception e) {
            return null;
        }
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public static NodeFactory getInstance() {
        return INSTANCE;
    }
}
