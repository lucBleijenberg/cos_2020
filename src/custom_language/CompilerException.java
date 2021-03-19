package custom_language;

/**
 * this exception is thrown when (LeftRightLanguage) code is semantically incorrect,
 * or is otherwise not compilable
 */
public class CompilerException extends RuntimeException {

    public CompilerException(String message) {
        super(message);
    }

}
