package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Labels {

    private final Map<Type, Integer> labels = createLabels();

    private Map<Type, Integer> createLabels() {
        Map<Type, Integer> labels = new HashMap<>();
        Arrays.stream(Type.values()).forEach(type -> labels.put(type, 0));
        return labels;
    }

    /**
     * all label types
     */
    public enum Type {
        EndWhen,
        EndCondition,
        EndIf,
        Loop,
        EndLoop
    }

    /**
     * returns a label in the form `type_id`
     */
    public String get(Type type) {
        return type.name() + "_" + labels.get(type);
    }

    /**
     * updates a label
     */
    public void update(Type type) {
        labels.put(type, labels.get(type) + 1);
    }

}
