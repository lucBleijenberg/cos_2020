package custom_language.symbols;

/**
 * represents a symbol with a [name] and [type],
 * such as a method-parameter
 */
public class Symbol {

    public final String name;
    public final DataType type;

    public Symbol(String name, DataType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name + ": " + type;
    }
}