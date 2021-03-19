package custom_language.symbols;

/**
 * represents a data type,
 * used for expressions and method return types
 */
public enum DataType {
    VOID,
    BOOLEAN,
    INT,
    FLOAT,
    TEXT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
