package custom_language.symbols;

/**
 * represents a variable symbol
 * unlike [Symbol] this class also stores an [index],
 * which can be used as unique id's in bytecode generation
 */
public class VariableSymbol extends Symbol {
    public final int index;

    public VariableSymbol(String name, DataType type, int index) {
        super(name, type);
        this.index = index;
    }

}