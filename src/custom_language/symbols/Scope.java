package custom_language.symbols;

import java.util.HashMap;
import java.util.Map;

/**
 * represents a scope containing method declarations and variables
 *
 * to check if a variable is already present in `this` scope or one of it parents,
 *     use `Scope.getVariableSymbol(String name) != null`
 *
 * to check if a variable is already declared in `this` scope only,
 *     use `Scope.variableSymbols.containsKey(name: String)`
 */
public class Scope {

    public final Map<String, VariableSymbol> variableSymbols = new HashMap<>(); // name      <-> symbol
    public final Map<String, MethodSymbol> methodSymbols = new HashMap<>();     // signature <-> method

    public final Scope parent;

    /* --------------- constructor ---------- */

    /**
     * creates a new Scope with the given [parent]
     */
    public Scope(Scope parent) {
        this.parent = parent;
    }

    /* --------------- methods --------------- */

    /**
     * adds a new variable to this scope
     * this method does not check if there already exists a variable with the same name
     */
    public void addVariableSymbol(VariableSymbol symbol) {
        variableSymbols.put(symbol.name, symbol);
    }

    /**
     * adds a new method to this scope
     * this method does not check if there already exists a method with the same signature
     */
    public void addMethodSymbol(MethodSymbol symbol) {
        methodSymbols.put(symbol.signature, symbol);
    }

    /**
     * returns the symbol associated with the given [name],
     * or null when no symbol was found
     */
    public VariableSymbol getVariableSymbol(String name) {
        VariableSymbol symbol = variableSymbols.get(name);

        // symbol is in this scope
        if (symbol != null) {
            return symbol;
        }

        // look in parent scope (if there is a parent)
        return parent == null ? null : parent.getVariableSymbol(name);
    }

    /**
     * returns the method symbol associated with the given [signature] (MethodSymbol::signature),
     * or null when no matching method was found
     */
    public MethodSymbol getMethodSymbol(String signature) {
        MethodSymbol symbol = methodSymbols.get(signature);

        // symbol is in this scope
        if (symbol != null) {
            return symbol;
        }

        // look in parent scope (if there is a parent)
        return parent == null ? null : parent.getMethodSymbol(signature);
    }

}
