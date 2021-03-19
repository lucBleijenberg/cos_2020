
#LeftRightLanguage

This document explains all features of the LeftRightLanguage with code examples.
Some (correct and incorrect) example files are also present in the `\testFiles` directory.

#### Comments

Comments are often used to explain a piece of code and will not be executed by the compiler.

A comment can be created by placing one of the following symbols anywhere on a line:

    ~ ! @ # $ % ^ ( ) ' ` ?
    
Comments will end at the end of a line.

#### Data types

this language supports the following data types:

    (name)      (java-equivalent)
     void    ->  void
     0/1     ->  boolean    
     int     ->  int         
     float   ->  float      
     text    ->  String
   
**void**  
`void` is used only for methods that don't return anything
   
**0/1**  
`0/1` represents a boolean value,   
and can either be `on` or `off`

**int**  
`int` represents a whole number,   
such as `12345` 

**float**  
`float` represents a number with a fractional part,   
such as `3.14` or `0.9`

**text** 
`text` represents a word or a series of characters,  
such as `"Hello world"` or `"&$^@^(%$#"`

#### Operators

This language supports the following math operators, sorted by precedence (descending):

    (a vertical bar is used to denote a choice)
    
    (operator)         (meaning)
    *   |   times       multiply
    /   |   divide      divide
    -   |   minus       substract, negate (prefix)
    +   |   plus        add

the precedence of operators can be altered by putting an expression between square brackets, for example:  

    [a + b] * c

logical operators

    (a vertical bar is used to denote a choice)
    
    (operator)         (meaning)
    ==  |   is          equality
    !=  |   is not      non-equality
    >   |   bigger      bigger-than
    <   |   smaller     smaller-than
    &&  |   and         boolean and
    ||  |   or          boolean or
    
Example expressions:

    3 + 5 minus 4   
    
    4 < 5 and 5 < 6     
    
    3 is not 8
    
    3 + 5 * 3       @ 18
    
    [3 + 5] * 3     $ 24
    
**dont's**  

create variable with void-type

    void a = 3 $ cannot create void variable

compare incompatible types

    int a = 3
    float b = 3.14
    
    0/1 c = a > b    % cannot compare int with float
    
use `and`/`or` operator on non-boolean values

    0/1 a = 2 && 3   % `&&` can only be used on booleans 
    
using an expression as the only statement of a line

    3 + 5            @ fix: int a = 3 + 5 (see variables)
    
#### Variables

the general way to declare variables is:

    type name (= value)
    
a variable can either be declared and initialized directly,  
or be initialized later.  
example of variable declarations are:

    int a                   @ initialize later
    float b = 5
    text c = "Hello World"
    0/1 d = on
        
**dont's**
  
assigning wrong type
    
    int a
    a = "abc"    @ cannot assign text to int

re-declaring variables  
  
    int a
    text a       # variable `a` is already declared
    
referencing undeclared variables

    println[a]   $ variable `a` is not declared
    
#### Methods

the general way do declare a method is:

    type name {arguments} [statements]
    
to return a value from a method, use `<- value`.  
to return from a void method, use `<- end`.

Code inside a method cannot access global variables.  
A (non-void) method can be used as either a statement or expression.
    
the general way to call a method is:

    name [arguments]
    
##### Built-in methods

    (a ? is used to denote any type is allowed)
    
    void prin[? message]       @ prints `message`

    void println[? message]    # print `message`, followed by a newline
    
    text readln[]              $ reads a line from the console
    
##### Examples

Some example methods:

    @
    @ adds two integers
    @
    int add {int a, int b} [
        <- a + b
    ]
    
    ^
    ^ prints `a`, and also `b` if it is positive (0..)
    ^
    void method {float a, float b} [
        println[a]
        
        when {b < 0} [<- end]
        
        println[b]
    ]
    
    )
    ) prints `a`, and also `b` if it is positive (0..)
    )
    void method2 {float a, float b} [
        println[a]
        
        when {b > 0 or b is 0} [ println[b] ]
    ]
    
Methods with the same name and the same argument types are considered equal. (argument names don't matter)   
Because of this, it is also possible to create "overload" methods, for example:

    void a {} []
    
    void a {int a} []
    
    void a {int a, int b} []
    
    void a {float a, float b} []
    
However, it's not possible to overload built-in methods.
    
**dont's**  

redefine a method

    void a {int a} [] 
    
    void a {int b} []    $ error! `a(int)` is already declared !
    
duplicate method arguments

    void method {int a, int a} []    @ duplicate method argument: `a`
    
return wrong type in method

    void a {} [
        <- 4      @ error ! cannot return int form void method
    ]
    
    int b {} [
        <- end    $ error ! int method cannot return void
    ]
    
    text c {} [
        <- 3.14   % error ! cannot return float from text method
    }
    
local methods

    int a {} [
        int b {} []  $ error ! local methods are not supported
    ]
    
access global variables from methods

    int a = 3
    
    void method {} [
        println[a]    % error ! method cannot access gloval variable `a`
    ]
    
non-void method without return statement

    int x {} [
        @ error ! no return statement
    ]
    
overload built-in methods

    void println[int a, int b]  @ `println` is a built in method
    
    int readln[int a]           ^ `readln` is a built in method
    
#### Loops

In order to execute a piece of code multiple times, a for-loop can be used.  
A for-loop has 2 variants:

    for i = 0..9        [statements]
    for i = 0..9 step 2 [statements]
    
In both examples `0` is the start index and `9` is the stop index  
On each iteration the variable `i` will hold the current loop index.  

The following code will print `0` `1` `2` `3` `4` `5` `6` `7` `8` `9` (each on a new line)

    for i = 0..9 [ println[i] ]
    
The following code will print `0` `2` `4` `6` `8` (each on a new line)

    for i = 0..9 step 2 [ println[i] ]
    
A for loop has it's own scope that inherits from the outer scope.
For example:

    text a = "global"
    for a = 0..9 [ println[a] ]     @ prints 0 1 2 3 4 5 6 7 8 9
    
    for b = 3..6 [ println[a] ]     $ prints "global" each iteration
    
    for c = 0..9 [
        println[c]                      @ refers to `c` in outer loop      
        for c = 9..0 [ println[c] ]     $ refers to `c` in inner loop
    ]
    
**Note:** When the compiler sees a loop, it will first initialize a variable to `start`.  
It then checks whether that variable is smaller than `end`, on each iteration.  
At the end of each iteration, the variable will be increased by `step`, or `1` when there is no step specified.

This is important to know, to avoid confusions. below are some examples to clarify:

    for i = 9..0 [ println[i] ]          @ prints nothing (`i` is always bigger than `end`)
    
    for i = 9..0 step -1 [ println[i] ]  % prints nothing (`i` is always bigger than `end`)
    
    for i = 0..9 step -1 [ println[i] ]  # infinite loop (`i` never gets bigger than `end`)
    
**dont's**  

use a different type than `int` for `start`, `step` or `stop`:

    for i = 0.0..4.3 []       @ start and stop must be int value
    
    for i = on..off []        # start and stop must be int value
    
    for i = "abc".."xyz" []   % start and stop must be int value
    
    for i = 0..2 step 0.1 []  ^ step must be a int value
    
#### Conditional statements

In order to execute a piece of code only when a condition is met, a `when` statement can be used.

the general structure of a when statements is:

    when
        {conditionA} [ a[] ]
        {conditionB} [ b[] ]
        otherwise    [ c[] ]
        
A when statement can have one or more when-clauses (`{condition}[statements]`), and an optional `otherwise` clause.  

The compiler executes the statements in the first when-clause where the condition results in true (`on`)  
If no condition is true, the statements in the `otherwise` clause are executed
    
For example, in the following code:

    @ `a` and `b` are boolean values

    when 
        {a}       [ a[] ] 
        {b}       [ b[] ]
        otherwise [ c[] ]
        
if `a` is `on`, the method `a[]` will be called  
if `b` is `on`, the method `b[]` will be called  
else the method `c[]` will be called
        
Each when-clause (and the otherwise clause) has it's own scope.  
The following code illustrates this point:

    # `a` is an int value
    
    when 
        {a < 5} [ 
            text a = "hello"
            println[a]          @ prints (local) "hello"
        ]
        {a > 5} [
            println[a]          $ prints (global) 5
        ]
        otherwise [
            float a = 3.14      ^ prints (local) 3.14
        ]
    
#### Keywords
    
the following is a list of all keywords, and can thus not be used as an identifier for a variable or method:

    void
    int
    float
    text
    
    on
    off
    end
    
    for 
    when
    otherwise
    
    plus
    minus
    times
    divide
    
    and
    or
    is
    smaller
    larger
    