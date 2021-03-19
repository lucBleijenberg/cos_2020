package custom_language.visitors;

import custom_language.symbols.*;
import custom_language.LeftRightLanguageBaseVisitor;
import custom_language.LeftRightLanguageParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import util.Util;

import java.util.List;
import java.util.stream.Collectors;

import static util.Util.require;

/**
 * Visitor that determines the data types of expressions and selectively associates
 * the datatype with the parse tree node in the ParseTreeProperty.
 */
public class Checker extends LeftRightLanguageBaseVisitor<DataType> {

    // parse tree properties
    private final ParseTreeProperty<Symbol> symbols;
    public final ParseTreeProperty<DataType> types;

    /**
     * the current scope for variables / methods
     */
    private Scope scope = new Scope(null);

    /**
     * used to create unique variable indices
     */
    private int variableIndex = 1;

    /**
     * the current method
     * used to check if return statements return correct type
     */
    private MethodSymbol method = new MethodSymbol("main", DataType.VOID);

    //--------------------------------------------------------------------------------

    public Checker(ParseTreeProperty<DataType> types, ParseTreeProperty<Symbol> symbols) {
        this.types = types;
        this.symbols = symbols;
    }

    //--------------------------------------------------------------------------------

    /**
     * temporarily sets [scope] to [newScope],
     * and executes the given [runnable]
     * afterwards resets the current scope to it's old value
     */
    private void withScope(Scope newScope, Runnable runnable) {
        Scope oldScope = scope;
        scope = newScope;

        runnable.run();

        scope = oldScope;
    }

    /**
     * temporarily sets [method] to [symbol],
     * and executes the given [runnable]
     * afterwards resets [method] to it's old value
     */
    private void withMethod(MethodSymbol symbol, Runnable runnable) {
        MethodSymbol oldMethod = method;
        method = symbol;

        runnable.run();

        method = oldMethod;
    }

    /**
     * returns [type] after adding it to the types [ParseTreeProperty]
     */
    private DataType saveType(ParseTree context, DataType type) {
        types.put(context, type);
        return type;
    }

    /**
     * returns the correct [DataType] based on the given [context],
     * which is the corresponding type to the first non-null child
     */
    private DataType typeOf(LeftRightLanguageParser.TypeContext context) {
        if (context.BOOLEAN_TYPE() != null) return DataType.BOOLEAN;
        if (context.TEXT_TYPE() != null)    return DataType.TEXT;
        if (context.INT_TYPE() != null)     return DataType.INT;
        if (context.FLOAT_TYPE() != null)   return DataType.FLOAT;
        if (context.VOID_TYPE() != null)    return DataType.VOID;

        // can only get here if DataType / grammar was changed
        throw new IllegalStateException("no type found in `" + context.getText() + "`");
    }

    /**
     * if the type of the [leftTree] is equal to the type of the [rightTree],
     * returns that type.
     * Else throws a [CompilerException]
     */
    private DataType commonDataType(ParseTree leftTree, ParseTree rightTree) {
        DataType left = visit(leftTree);
        DataType right = visit(rightTree);

        require(left == right, "incompatible types: `" + left + "` and `" + right + "`");
        return left;
    }

    //---------------------------------------------------------------------------------

    @Override
    public DataType visitLogicalExpression(LeftRightLanguageParser.LogicalExpressionContext context) {
        commonDataType(context.left, context.right); // check if types are the same and visit
        return saveType(context, DataType.BOOLEAN);
    }

    @Override
    public DataType visitAndExpression(LeftRightLanguageParser.AndExpressionContext context) {
        DataType type = commonDataType(context.left, context.right);
        require(type == DataType.BOOLEAN, context.operator.getText() + " only applicable to booleans");
        return saveType(context, type);
    }

    @Override
    public DataType visitOrExpression(LeftRightLanguageParser.OrExpressionContext context) {
        DataType type = commonDataType(context.left, context.right);
        require(type == DataType.BOOLEAN, context.operator.getText() + " only applicable to booleans");
        return saveType(context, type);
    }

    /**
     * visits a declaration statement and adds a symbol to the current scope.
     * if there is already a symbol with the same name, throws a `CompilerException`
     */
    @Override
    public DataType visitDeclaration(LeftRightLanguageParser.DeclarationContext context) {
        ParseTree identifier = context.ID() != null
            ? context.ID()
            : context.assignment().ID();

        String name = identifier.getText();
        DataType type = typeOf(context.type());

        // check if type in not VOID and variable is not already declared
        require(type != DataType.VOID, "illegal type `void`: " + context.getText());
        require(!scope.variableSymbols.containsKey(name), "variable is already declared: `" + name + "`");

        scope.addVariableSymbol(new VariableSymbol(name, type, variableIndex++));

        // direct initialization
        if (context.assignment() != null) {
            visit(context.assignment());
        }
        return null;
    }

    /**
     * visits an identifier and looks up a symbol in the current scope (and it's parents)
     * if no symbol was found, throws a `CompilerException`
     */
    @Override
    public DataType visitReferenceExpression(LeftRightLanguageParser.ReferenceExpressionContext context) {
        String name = context.getText();
        VariableSymbol symbol = scope.getVariableSymbol(name);

        require(symbol != null, "unknown variable: `" + name + "`");

        // add symbol and type
        types.put(context, symbol.type);
        symbols.put(context, symbol);
        return symbol.type;
    }

    /**
     * visits a method and adds it to the current [scope]
     * if there already exists a method with the same signature, throws a [CompilerException]
     */
    @Override
    public DataType visitMethod(LeftRightLanguageParser.MethodContext context) {
        String name = context.ID().getText();
        DataType type = typeOf(context.type());

        List<LeftRightLanguageParser.ArgumentContext> arguments = context.arguments().argument();

        // create method scope
        Scope methodScope = new Scope(null);

        // all argument symbols
        Symbol[] methodArguments = arguments.stream()
            .map(a -> new Symbol(a.ID().getText(), typeOf(a.type())))   // map to symbols
            .collect(Collectors.toList())
            .toArray(new Symbol[arguments.size()]);                     // convert to array

        // add all arguments
        withScope(methodScope, () -> addArguments(arguments));

        MethodSymbol methodSymbol = new MethodSymbol(name, type, methodArguments);

        // check if method already exists (by signature)
        require(
            scope.getMethodSymbol(methodSymbol.signature) == null,
            "method already exists: `" + methodSymbol.signature + "`"
        );

        withMethod(methodSymbol, () -> {

            // visit all statements
            withScope(methodScope, () -> context.statement().forEach(this::visit));

            // there is no return statement (in non-void method)
            if (method.type != DataType.VOID) {
                require(method.returns, "method must have a return statement");
            }
        });

        scope.addMethodSymbol(methodSymbol);
        symbols.put(context, methodSymbol);
        return null;
    }

    /**
     * used to add all argument symbols of a method to the current scope
     * if a duplicate method argument is found, a [CompilerException] is thrown
     */
    private void addArguments(List<LeftRightLanguageParser.ArgumentContext> arguments) {
        for (int i = 0; i < arguments.size(); i++) {
            LeftRightLanguageParser.ArgumentContext a = arguments.get(i);

            VariableSymbol symbol = new VariableSymbol(a.ID().getText(), typeOf(a.type()), i);

            require(
                scope.getVariableSymbol(symbol.name) == null,
                "duplicate method argument: " + symbol.name
            );
            scope.addVariableSymbol(symbol);
        }
    }

    /**
     * visits a method call and checks whether there exists a method with the same signature,
     * by recreating the signature from the supplied arguments
     * if no matching method was found, a [CompilerException] is thrown
     */
    @Override
    public DataType visitMethodCall(LeftRightLanguageParser.MethodCallContext context) {
        String name = context.ID().getText();

        StringBuilder builder = new StringBuilder();

        // visit each argument and build method signature
        for (ParseTree tree : context.parameters().expression()) {
            DataType argumentType = visit(tree);
            builder.append(Util.typeDescriptor(argumentType));
        }
        String signature = name + "(" + builder.toString() + ")";

        MethodSymbol methodSymbol = scope.getMethodSymbol(signature);

        // check if method exists with same signature
        require(methodSymbol != null, "method '" + signature + "' does not exist");

        return saveType(context, methodSymbol.type);
    }

    /**
     * visits an assignment and checks if the correct type is being assigned,
     * and whether the referenced variable actually exists.
     * If this is not the case, a [CompilerException] is thrown
     */
    @Override
    public DataType visitAssignment(LeftRightLanguageParser.AssignmentContext context) {
        String name = context.ID().getText();
        Symbol symbol = scope.getVariableSymbol(name);

        DataType type = visit(context.expression());

        require(symbol != null, "unknown variable: `" + name + "`");
        require(type == symbol.type, "cannot assign `" + type + "` to `" + symbol.type + "`");

        // add symbol
        symbols.put(context, symbol);
        return null;
    }

    /**
     * visits for loop and adds the loop variable to the current [scope]
     * also allocates room to store the `stop` and `step` values (not as variables)
     */
    @Override
    public DataType visitForStatement(LeftRightLanguageParser.ForStatementContext context) {
        withScope(new Scope(scope), () -> {
            VariableSymbol symbol = new VariableSymbol(context.ID().getText(), DataType.INT, variableIndex++);

            scope.addVariableSymbol(symbol);
            symbols.put(context, symbol);

            variableIndex += 2; // allocate room to store stop and step
            visitChildren(context);
        });
        return null;
    }

    /**
     * visits a return statement and checks if it is appropriate in the current context
     */
    @Override
    public DataType visitReturnStatement(LeftRightLanguageParser.ReturnStatementContext context) {
        DataType methodType = method.type;

        // check if (non-void) method returns value
        if (context.END() != null) {
            require(methodType == DataType.VOID, "method must return `" + methodType + "`");
            return null;
        }

        // save type of return value
        DataType type = visit(context.expression());

        // check if return type matches method type
        require(methodType == type, "cannot return `"  + type + "` from `" + methodType + "` method");

        method.returns = true;
        return null;
    }

    /**
     * visits a when statement to make sure each when-clause (and the otherwise clause)
     * has a own scope
     */
    @Override
    public DataType visitWhen(LeftRightLanguageParser.WhenContext context) {

        // visit each when clause with a new scope
        context.whenClause().forEach(clause ->
            withScope(new Scope(scope), () -> visit(clause))
        );

        // visit otherwise statements with new scope
        withScope(new Scope(scope), () -> context.statement().forEach(this::visit));
        return null;
    }

    //-----------------------------------------------------------------------------------

    @Override
    public DataType visitLiteralExpression(LeftRightLanguageParser.LiteralExpressionContext context) {
        return saveType(context, visit(context.literal()));
    }

    @Override
    public DataType visitIntLiteral(LeftRightLanguageParser.IntLiteralContext context) {
        return saveType(context, DataType.INT);
    }

    @Override
    public DataType visitTextLiteral(LeftRightLanguageParser.TextLiteralContext context) {
        return saveType(context, DataType.TEXT);
    }

    @Override
    public DataType visitFloatLiteral(LeftRightLanguageParser.FloatLiteralContext context) {
        return saveType(context, DataType.FLOAT);
    }

    @Override
    public DataType visitBooleanLiteral(LeftRightLanguageParser.BooleanLiteralContext context) {
        return saveType(context, DataType.BOOLEAN);
    }

    @Override
    public DataType visitMultiplyExpression(LeftRightLanguageParser.MultiplyExpressionContext context) {
        return saveType(context, commonDataType(context.left, context.right));
    }

    @Override
    public DataType visitDivideExpression(LeftRightLanguageParser.DivideExpressionContext context) {
        return saveType(context, commonDataType(context.left, context.right));
    }

    @Override
    public DataType visitAddExpression(LeftRightLanguageParser.AddExpressionContext context) {
        return saveType(context, commonDataType(context.left, context.right));
    }

    @Override
    public DataType visitMinExpression(LeftRightLanguageParser.MinExpressionContext context) {
        return saveType(context, commonDataType(context.left, context.right));
    }

    @Override
    public DataType visitNegateExpression(LeftRightLanguageParser.NegateExpressionContext context) {
        return saveType(context, visit(context.expression()));
    }

    @Override
    public DataType visitGroupExpression(LeftRightLanguageParser.GroupExpressionContext context) {
        return saveType(context, visit(context.expression()));
    }

    @Override
    public DataType visitMethodExpression(LeftRightLanguageParser.MethodExpressionContext context) {
        return saveType(context, visit(context.methodCall()));
    }

    @Override
    public DataType visitReadExpression(LeftRightLanguageParser.ReadExpressionContext context) {
        return saveType(context, DataType.TEXT);
    }

}






