package nl.saxion.cos;

import custom_language.*;
import custom_language.visitors.Checker;
import custom_language.symbols.DataType;
import custom_language.symbols.Symbol;
import custom_language.visitors.CodeGenerator;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import util.Files;
import util.Util;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Compiles source code in a custom language into Jasmin and then assembles a
 * JVM-compatible .class file.
 */
public class Compiler {

	/**
	 * The number of errors detected by the lexer and parser.
	 */
	private int errorCount = 0;

	private final ParseTreeProperty<DataType> types = new ParseTreeProperty<>();
	private final ParseTreeProperty<Symbol> symbols = new ParseTreeProperty<>();

	/**
	 * Compiles a complete source code file.
	 *
	 * @param inputPath Path to the source code to compile.
	 * @throws IOException if files could not be read or written
	 */
	public String compileFile(String inputPath) throws IOException {
		return compile(CharStreams.fromFileName(inputPath));
	}

	/**
	 * Compiles a string.
	 * @param sourceCode The source code to compile.
	 */
	public String compileString(String sourceCode) {
		return compile(CharStreams.fromString(sourceCode));
	}

	/**
	 * Compiles a file. The source code is lexed (turned into tokens), parsed (a parse tree created)
	 * then Jasmin code is generated and assembled into a class.
	 * @param input Stream to the source code input.
	 */
	private String compile(CharStream input) {

		// Phase 1: Run the lexer
		CommonTokenStream tokens = runLexer(input);

		// Phase 2: Run the parser
		ParseTree parseTree = runParser(tokens);

		// exit if source file contains errors
		if (errorCount > 0) {
			return null;
		}

		// Phase 3: Check the source code for semantic errors
		if (!runChecker(parseTree)) {
			return null;
		}

		// Phase 4: Generate code
		return generateCode(parseTree);
	}

	/**
	 * Take the character input and turn it into tokens according to the grammar.
	 * @param input The input.
	 * @return A steam of tokens.
	 */
	private CommonTokenStream runLexer(CharStream input) {
		LeftRightLanguageLexer lexer = new LeftRightLanguageLexer(input);
		lexer.addErrorListener(getErrorListener());
		return new CommonTokenStream(lexer);
	}

	/**
	 * Tries to form a parse tree from the given tokens. In case of errors, the error listener is
	 * called, but the parser still tries to create a parse tree.
	 * @param tokens  The tokens returned from the lexer.
	 * @return        A Parse Tree.
	 */
	private ParseTree runParser(CommonTokenStream tokens) {
		LeftRightLanguageParser parser = new LeftRightLanguageParser(tokens);
		parser.addErrorListener(getErrorListener());
		return parser.program();
	}

	/**
	 * Called to check if the source code was semantically correct. This method is only called when
	 * there were no syntax errors.
	 *
	 * @param parseTree The parse tree generated by the parser
	 * @return True if all code is semantically correct
	 */
	private boolean runChecker(ParseTree parseTree) {
		try {
			Checker checker = new Checker(types, symbols);
			checker.visit(parseTree);
			return true;
		}
		catch (CompilerException exception) {
			System.err.println(exception.getMessage());
			return false;
		}
	}

	/**
	 * Generate the Jasmin code for the source code. This method is only called after checking that
	 * the code is syntactically and semantically correct, so you need not check for any errors.
	 *
	 * @param parseTree The parseTree to generate code for
	 * @return All Jasmin code that is generated
	 */
	private String generateCode(ParseTree parseTree) {
		CodeGenerator generator = new CodeGenerator(types, symbols);
		generator.visit(parseTree);
		return generator.getCode();
	}

	/**
	 * Creates and returns an error listener for use in the lexer and parser that just increases
	 * the errorCount-attribute in this class so we can find out if the source code had a syntax
	 * error.
	 *
	 * @return  An error listener for use with lexer.addErrorListener() and parser.addErrorListener()
	 */
	private ANTLRErrorListener getErrorListener() {
		return new BaseErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
			                         int line, int charPositionInLine,
			                         String msg, RecognitionException e) {
				errorCount++;
			}
		};
	}

	/**
	 * extracts a class name from the given [path]
	 * the class name will be the (relative) name of the corresponding file, without extension
	 * for example: `a/b/File.txt` will result in "File"
	 */
	private static String extractClassName(Path path) {
		String sourceFileName = path.getFileName().toString();
		int dotIndex = sourceFileName.lastIndexOf('.');
		return sourceFileName.substring(0, dotIndex == -1 ? sourceFileName.length() : dotIndex);
	}

	/**
	 * main method.
	 * You can use [arguments] to supply the file name to compile.
	 */
	public static void main(String[] arguments) {
		try {
			Compiler compiler = new Compiler();

			// ensure there is at least 1 argument
			if (arguments.length == 0) {
				throw new IllegalArgumentException("arguments cannot be empty");
			}

			Path path = Paths.get(arguments[0]);
			String className = extractClassName(path);

			// Read the file and compile it.
			String bytecode = compiler.compileFile(path.toString());
			if (bytecode == null) {
				System.err.println("No Jasmin output");
				return;
			}

			String jasminFilename = path.getParent().resolve(className + ".j").toString();
			String classFileName  = path.getParent().resolve(className + ".class").toString();

			// write jasmin to file
			Files.writeToFile(jasminFilename, bytecode);

			// write to .class file
			Files.writeToFile(classFileName, Files.toClassBytes(jasminFilename, bytecode));
		}
		catch (IOException | AssembleException e) {
			System.err.println("Something went wrong: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
