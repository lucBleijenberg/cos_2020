package util;

import custom_language.CompilerException;
import custom_language.symbols.DataType;

public class Util {

    private Util() {}

    /**
     * if [predicate] is false, throws a [CompilerException]
     */
    public static void require(boolean predicate, String error) {
        if (!predicate) {
            throw new CompilerException(error);
        }
    }

    /**
     * returns a conditional jump for the given `operator`
     * @throws IllegalArgumentException,
     *     if `operator` is invalid
     */
    public static String if_cmp(String operator) {
        switch (operator) {
            case "==":    return "if_icmpeq";
            case "!=":    return "if_icmpne";
            case "<":     return "if_icmplt";
            case ">":     return "if_icmpgt";
        }
        throw new IllegalArgumentException("unknown operator: `" + operator + "`");
    }

    /**
     * returns a bytecode type mnemonic,
     * for the given [type]
     */
    public static char typeMnemonic(DataType type) {
        switch (type) {
            case INT:  return 'i';
            case TEXT:    return 'a';
            case FLOAT:   return 'f';
            case BOOLEAN: return 'i'; // same as int
            default: throw new IllegalArgumentException("invalid type: `" + type + "`");
        }
    }

    /**
     * returns a bytecode type descriptor,
     * for the given [type]
     */
    public static String typeDescriptor(DataType type) {
        switch (type) {
            case INT:  return "I";
            case TEXT:    return "Ljava/lang/String;";
            case FLOAT:   return "F";
            case BOOLEAN: return "Z";
            case VOID:    return "V";
            default: throw new IllegalArgumentException("unknown type: `" + type + "`");
        }
    }

}
