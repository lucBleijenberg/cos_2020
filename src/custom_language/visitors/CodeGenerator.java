package custom_language.visitors;

import custom_language.LeftRightLanguageBaseVisitor;
import custom_language.LeftRightLanguageParser;
import custom_language.symbols.DataType;
import custom_language.symbols.MethodSymbol;
import custom_language.symbols.Symbol;
import custom_language.symbols.VariableSymbol;
import org.antlr.v4.runtime.tree.ParseTree;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import util.Labels;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static util.Labels.Type;
import static util.Util.*;

/**
 * this class can be used to traverse a ParserTree generated by ANTLR
 * to start traversing a parse tree, use `generator.visit(parseTree)`
 */
public class CodeGenerator extends LeftRightLanguageBaseVisitor<Void> {

    // parse tree properties
    private final ParseTreeProperty<DataType> types;
    private final ParseTreeProperty<Symbol> symbols;

    /**
     * used to execute code after the bytecode for the main method is generated
     */
    private final List<Runnable> postPoned = new ArrayList<>();

    /**
     * used to join all bytecode instructions to one string
     */
    public final StringJoiner bytecode = new StringJoiner("\n");

    /**
     * returns the generated code
     */
    public String getCode() {
        return bytecode.toString();
    }

    //---------------------------------------------------------------------------------------------

    public CodeGenerator(ParseTreeProperty<DataType> types, ParseTreeProperty<Symbol> symbols) {
        this.types = types;
        this.symbols = symbols;
    }

    //---------------------------------------------------------------------------------------------

    /**
     * used for managing labels in the bytecode
     */
    private final Labels labels = new Labels();

    /**
     * visits the given `tree`, if it is not null
     */
    private void visitIfPresent(ParseTree tree) {
        if (tree != null) visit(tree);
    }

    /**
     * tries to get a value from [property] with [tree] as key
     * if no such value exists, throws an [IllegalArgumentException]
     */
    private static <T> T tryToGet(ParseTreeProperty<T> property, ParseTree tree) {
        T value = property.get(tree);

        if (value == null) {
            throw new IllegalArgumentException(
                "property is null for `" + tree.getText() + " " + tree.getClass().getSimpleName()
            );
        }
        return value;
    }

    /**
     * tries to return `DataType.BOOLEAN`
     * if the type associated with the given [tree] is not a boolean,
     * an [CompilerException] is thrown
     */
    private void requireBoolean(ParseTree tree) {
        DataType type = tryToGet(types, tree);
        require(
            type == DataType.BOOLEAN,
            "expected a boolean expression, found " + type + ": `" + tree.getText() + "`"
        );
    }

    //-----------------------------------------------------------------------------------------------------

    /**
     * the start of a program
     * first adds code for the main method,
     * and then executes all functions in [postPoned], if any (for example to add methods)
     */
    @Override
    public Void visitProgram(LeftRightLanguageParser.ProgramContext context) {
        bytecode.add(".class public Main");
        bytecode.add(".super java/lang/Object");
        bytecode.add("");
        bytecode.add(".method public static main([Ljava/lang/String;)V");
        bytecode.add(".limit stack 98");
        bytecode.add(".limit locals 98");
        bytecode.add("");

        // visit all statements
        visitChildren(context);

        bytecode.add("return");
        bytecode.add(".end method");

        // execute 'postponed' statements
        postPoned.forEach(Runnable::run);
        return null;
    }

    /**
     * generates an `[type]store` instruction,
     * to update a variable
     */
    @Override
    public Void visitAssignment(LeftRightLanguageParser.AssignmentContext context) {
        VariableSymbol symbol = (VariableSymbol) tryToGet(symbols, context);

        visit(context.expression());

        // update variable
        String instruction = typeMnemonic(symbol.type) + "store " + symbol.index;
        bytecode.add(instruction);
        return null;
    }

    /**
     * visits a statement, which doesn't return anything
     */
    @Override
    public Void visitStatement(LeftRightLanguageParser.StatementContext context) {
        context.children.forEach(this::visitIfPresent);
        return null;
    }

    /**
     * represents the method `readln[]`
     * adds code to read a line from the console
     */
    @Override
    public Void visitReadExpression(LeftRightLanguageParser.ReadExpressionContext context) {
        bytecode.add("new java/util/Scanner");
        bytecode.add("dup");
        bytecode.add("getstatic java/lang/System/in Ljava/io/InputStream;");
        bytecode.add("invokenonvirtual java/util/Scanner/<init>(Ljava/io/InputStream;)V");
        bytecode.add("invokevirtual java/util/Scanner/nextLine()Ljava/lang/String;");
        return null;
    }

    /**
     * represents the method `print[? x]`
     * prints a value to the console
     */
    @Override
    public Void visitPrintStatement(LeftRightLanguageParser.PrintStatementContext context) {
        bytecode.add("getstatic java/lang/System/out Ljava/io/PrintStream;");
        visit(context.expression());

        DataType type = tryToGet(types, context.expression());
        String kind = typeDescriptor(type);

        bytecode.add("invokevirtual java/io/PrintStream/print(" + kind + ")V");
        return null;
    }

    /**
     * represents the method `println[? x]`
     * prints a value followed by a newline to the console
     */
    @Override
    public Void visitPrintLineStatement(LeftRightLanguageParser.PrintLineStatementContext context) {
        bytecode.add("getstatic java/lang/System/out Ljava/io/PrintStream;");
        visit(context.expression());

        DataType type = tryToGet(types, context.expression());
        String kind = typeDescriptor(type);

        bytecode.add("invokevirtual java/io/PrintStream/println(" + kind + ")V");
        return null;
    }

    //----------------------------------------------------------------------------------

    @Override
    public Void visitIntLiteral(LeftRightLanguageParser.IntLiteralContext context) {
        bytecode.add("ldc " + context.getText());
        return null;
    }

    @Override
    public Void visitFloatLiteral(LeftRightLanguageParser.FloatLiteralContext context) {
        bytecode.add("ldc " + context.getText());
        return null;
    }

    @Override
    public Void visitTextLiteral(LeftRightLanguageParser.TextLiteralContext context) {
        bytecode.add("ldc " + context.getText());
        return null;
    }

    @Override
    public Void visitBooleanLiteral(LeftRightLanguageParser.BooleanLiteralContext context) {
        String value = context.ON() != null ? "1" : "0";
        bytecode.add("ldc " + value);
        return null;
    }

    //-----------------------------------------------------------------------------

    @Override
    public Void visitAddExpression(LeftRightLanguageParser.AddExpressionContext context) {
        visit(context.left);
        visit(context.right);
        DataType type = tryToGet(types, context);

        bytecode.add(typeMnemonic(type) + "add");
        return null;
    }

    @Override
    public Void visitMultiplyExpression(LeftRightLanguageParser.MultiplyExpressionContext context) {
        visit(context.left);
        visit(context.right);
        DataType type = tryToGet(types, context);

        bytecode.add(typeMnemonic(type) + "mul");
        return null;
    }

    @Override
    public Void visitDivideExpression(LeftRightLanguageParser.DivideExpressionContext context) {
        visit(context.left);
        visit(context.right);
        DataType type = tryToGet(types, context);

        bytecode.add(typeMnemonic(type) + "div");
        return null;
    }

    @Override
    public Void visitMinExpression(LeftRightLanguageParser.MinExpressionContext context) {
        visit(context.left);
        visit(context.right);
        DataType type = tryToGet(types, context);

        bytecode.add(typeMnemonic(type) + "sub");
        return null;
    }

    @Override
    public Void visitNegateExpression(LeftRightLanguageParser.NegateExpressionContext context) {
        visit(context.expression());
        DataType type = tryToGet(types, context);
        bytecode.add(typeMnemonic(type) + "neg");
        return null;
    }

    //---------------------------------------------------------------------------------


    @Override
    public Void visitMethodCall(LeftRightLanguageParser.MethodCallContext context) {
        StringBuilder builder = new StringBuilder();

        // visit arguments and build method signature
        for (ParseTree argument : context.parameters().expression()) {
            visit(argument);
            builder.append(typeDescriptor(tryToGet(types, argument)));
        }
        String signature =
            context.ID().getText() + "(" +  builder.toString() + ")" + typeDescriptor(tryToGet(types, context));

        bytecode.add("invokestatic Main/" + signature);
        return null;
    }

    /**
     * generates code for a method, but doesn't immediately add it to the [bytecode]
     */
    @Override
    public Void visitMethod(LeftRightLanguageParser.MethodContext context) {
        postPoned.add(() -> {
            MethodSymbol symbol = (MethodSymbol) tryToGet(symbols, context);

            bytecode.add("");
            bytecode.add(".method public static " + symbol.fullSignature());
            bytecode.add(".limit stack 98");
            bytecode.add(".limit locals 98");
            bytecode.add("");

            context.statement().forEach(this::visit);

            bytecode.add("return");
            bytecode.add(".end method");
            bytecode.add("");
        });
        return null;
    }

    /**
     * generates a "[type]load [id]" bytecode statement for this reference,
     * where the [type] and [id] of the symbol have been resolved by the [Checker]
     */
    @Override
    public Void visitReferenceExpression(LeftRightLanguageParser.ReferenceExpressionContext context) {
        VariableSymbol symbol = (VariableSymbol) tryToGet(symbols, context);
        bytecode.add(typeMnemonic(symbol.type) + "load " + symbol.index);
        return null;
    }

    /**
     * visits a when statement
     */
    @Override
    public Void visitWhen(LeftRightLanguageParser.WhenContext context) {
        context.whenClause().forEach(this::visit);                    // ...
        context.statement().forEach(this::visit);                     // ...

        // end of when expression / statement
        bytecode.add(labels.get(Type.EndWhen) + ":");                 // EndWhen_x:
        labels.update(Type.EndWhen);
        return null;
    }

    /**
     * visits a part of a when statement
     * executes a piece of code when true (1) is on the stack
     */
    @Override
    public Void visitWhenClause(LeftRightLanguageParser.WhenClauseContext context) {
        requireBoolean(context.condition);
        visit(context.condition);                                     // [0 | 1]

        bytecode.add("ifeq " + labels.get(Type.EndCondition));        // ifeq EndCondition_x  ; false

        context.statement().forEach(this::visit);                     //     ...

        bytecode.add("goto " + labels.get(Type.EndWhen));             //     goto EndWhen_y   ; generated
        bytecode.add(labels.get(Type.EndCondition) + ":");            // EndCondition_x:

        labels.update(Type.EndCondition);
        return null;
    }

    /**
     * puts a boolean value on the stack (0 | 1),
     * depending on the expression
     */
    @Override
    public Void visitLogicalExpression(LeftRightLanguageParser.LogicalExpressionContext context) {
        visit(context.left);                                          // [0 | 1]
        visit(context.right);                                         // [0 | 1]

        String check = if_cmp(context.operator.getText());
        bytecode.add(check + " " + labels.get(Type.EndCondition));    // if_icmp[...] EndCondition_x
        bytecode.add("ldc 0");                                        //    ldc 0 ; false
        bytecode.add("goto " + labels.get(Type.EndIf));               //    goto EndIf_y
        bytecode.add(labels.get(Type.EndCondition) + ":");            // EndCondition_x:
        bytecode.add("ldc 1");                                        //    ldc 1 ; true
        bytecode.add(labels.get(Type.EndIf) + ":");                   // EndIf_y:

        labels.update(Type.EndCondition);
        labels.update(Type.EndIf);
        return null;
    }

    /**
     * puts a boolean on the stack (0 or 1)
     * if both operands are true, puts `true`, else `false`
     */
    @Override
    public Void visitAndExpression(LeftRightLanguageParser.AndExpressionContext context) {
        requireBoolean(context.left);

        visit(context.left);                                          // [0 | 1]

        String outerLabel = labels.get(Type.EndCondition);
        labels.update(Type.EndCondition);
        bytecode.add("ifeq " + outerLabel);                           // ifeq EndCondition_x     ; jump if false

        visit(context.right);                                         //    [0 | 1]

        bytecode.add("ifeq " + labels.get(Type.EndCondition));        //    ifeq EndCondition_y  ; jump if false
        bytecode.add("ldc 1");                                        //        ldc 1 ; both true -> true
        bytecode.add("goto " + labels.get(Type.EndIf));               //        goto EndIf_x
        bytecode.add(labels.get(Type.EndCondition) + ":");            //    EndCondition_y:

        bytecode.add("ldc 0");                                        //    ldc 0 ; false
        bytecode.add("goto " + labels.get(Type.EndIf));               //    goto EndIf_x
        bytecode.add(outerLabel + ":");                               // EndCondition_x:
        bytecode.add("ldc 0");                                        //     ldc 0 ; false
        bytecode.add(labels.get(Type.EndIf) + ":");                   // EndIf_x:

        labels.update(Type.EndCondition);
        labels.update(Type.EndIf);
        return null;
    }

    /**
     * puts a boolean on the stack (0 or 1)
     * if either operand is true, puts `true`, else `false`
     */
    @Override
    public Void visitOrExpression(LeftRightLanguageParser.OrExpressionContext context) {
        requireBoolean(context.left);

        visit(context.left);                                          // [0 | 1]

        String outerLabel = labels.get(Type.EndCondition);
        labels.update(Type.EndCondition);
        bytecode.add("ifne " + outerLabel);                           // ifne EndCondition_x      ; jump if true

        visit(context.right);                                         //    [0 | 1]

        bytecode.add("ifne " + labels.get(Type.EndCondition));        //    ifne EndCondition_y   ; jump if true
        bytecode.add("ldc 0");                                        //        ldc 0 ; both false -> false
        bytecode.add("goto " + labels.get(Type.EndIf));               //        goto EndIf_x
        bytecode.add(labels.get(Type.EndCondition) + ":");            //    EndCondition_y:

        bytecode.add("ldc 1");                                        //    ldc 1 ; true
        bytecode.add("goto " + labels.get(Type.EndIf));               //    goto EndIf_x
        bytecode.add(outerLabel + ":");                               // EndCondition_x:
        bytecode.add("ldc 1");                                        //     ldc 1 ; true
        bytecode.add(labels.get(Type.EndIf) + ":");                   // EndIf_x:

        labels.update(Type.EndCondition);
        labels.update(Type.EndIf);
        return null;
    }

    //-----------------------------------------------------------------------------------

    /**
     * visits a for statement and generates bytecode to execute a piece of code a number of times,
     * based on the given `start`, `stop` and (optional) `step` variables
     */
    @Override
    public Void visitForStatement(LeftRightLanguageParser.ForStatementContext context) {
        VariableSymbol symbol = (VariableSymbol) tryToGet(symbols, context);

        int from = symbol.index;
        int to =   symbol.index + 1;
        int step = symbol.index + 2;

        visit(context.from);                                          // [Int]
        bytecode.add("istore " + from);                               // istore [from]

        visit(context.to);                                            // [Int]
        bytecode.add("istore " + to);                                 // istore [to]

        // if there is no step, an `iinc` instruction can be used
        String update = "iinc " + from + " 1";
        if (context.step != null) {
            visit(context.step);                                      // [Int]
            bytecode.add("istore " + step);                           // istore [step]

            update = "iload " + from + "\niload " + step +"\niadd\n istore " + from;
        }

        String loop = labels.get(Type.Loop);
        String endLoop = labels.get(Type.EndLoop);

        labels.update(Type.EndLoop);
        labels.update(Type.Loop);

        bytecode.add(loop + ":");                                     // Loop_x:
        bytecode.add("iload " + from);                                //     iload [from]
        bytecode.add("iload " + to);                                  //     iload [to]
        bytecode.add("if_icmpgt " + endLoop);                         //     if_icmgt EndLoop_x ; jump if too large

        context.statement().forEach(this::visit);                     //     ...

        bytecode.add(update);                                         //     [update]
        bytecode.add("goto " + loop);                                 //     goto Loop_x
        bytecode.add(endLoop + ":");                                  // EndLoop_x:
        return null;
    }

    @Override
    public Void visitReturnStatement(LeftRightLanguageParser.ReturnStatementContext context) {

        // non-void return
        if (context.expression() != null) {
            DataType type = tryToGet(types, context.expression());
            visit(context.expression());
            bytecode.add(Util.typeMnemonic(type) + "return");
            return null;
        }

        bytecode.add("return");
        return null;
    }

}

