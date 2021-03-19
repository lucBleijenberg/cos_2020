package custom_language.symbols;

import util.Util;

/**
 * represents a method with a unique [signature]
 */
public class MethodSymbol extends Symbol {

    /**
     * the bytecode signature of this method without the return type
     * for example: `a(ILjava/lang/String;)`
     */
    public final String signature;

    public final Symbol[] parameters;

    // whether this method has any explicit return statements
    public boolean returns = false;

    public MethodSymbol(String name, DataType type, Symbol... parameters) {
        super(name, type);
        this.parameters = parameters;
        this.signature = createSignature();
    }

    // create the (bytecode) signature for this method (without return type)
    private String createSignature() {
        StringBuilder arguments = new StringBuilder();

        for (Symbol argument : parameters) {
            arguments.append(Util.typeDescriptor(argument.type));
        }
        return name + "(" + arguments.toString() + ")";
    }

    /**
     * returns the full (bytecode) signature of this method, including return type
     * for example: `a(ILjava/lang/String;)V`
     */
    public String fullSignature() {
        return signature + Util.typeDescriptor(type);
    }

}
