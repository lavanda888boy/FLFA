# Parser & Building an Abstract Syntax Tree

### Course: Formal Languages & Finite Automata
### Author: Bajenov Sevastian

----

## Theory

`Parser` is a compiler that is used to break the data into smaller elements coming from lexical analysis phase. A parser takes input in the form of sequence of tokens and produces output in the form of parse tree. 

`Abstract Syntax Tree` is a kind of tree representation of the abstract syntactic structure of source code written in a programming language. Each node of the tree denotes a construct occurring in the source code. There is numerous importance of AST with application in compilers as abstract syntax trees are data structures widely used in compilers to represent the structure of program code. An AST is usually the result of the syntax analysis phase of a compiler. It often serves as an intermediate representation of the program through several stages that the compiler requires, and has a strong impact on the final output of the compiler.


## Objectives:

1. Get familiar with parsing, what it is and how it can be programmed.
2. Get familiar with the concept of AST.
3. In addition to what has been done in the 3rd lab work do the following:
   1. In case you didn't have a type that denotes the possible types of tokens you need to:
      1. Have a type __*TokenType*__ (like an enum) that can be used in the lexical analysis to categorize the tokens. 
      2. Please use regular expressions to identify the type of the token.
   2. Implement the necessary data structures for an AST that could be used for the text you have processed in the 3rd lab work.
   3. Implement a simple parser program that could extract the syntactic information from the input text.


## Implementation description

To start with, in this laboratory work I implemented the `parser` module inside of my project. In order to fulfill this task I, first of all, had to modify the `lexer` module. I grouped the keywords and their categories by using Java `enum` data type. It resulted in having two new enums: `Category` and `Operator`. Moreover, I partly introduced regular expressions to identify the type of tokens.

```
public enum Category {
    OPERATOR,
    VAR_NAME,
    VAR_VALUE,
    EXPRESSION_BORDER,
    ASSIGNMENT,
    ENDLINE
}
```

The first one includes all the posssible token categories in my language and the second one contains boolean operators supported by the language and a function `contains()` which is used inside of the `Lexer` class to distinguish the tokens.

```
public enum Operator {
    AND,
    OR, 
    NOT;
    
    public static boolean contains (String token) {
        for (Operator op : values()) {
            if (op.toString().equals(token)) {
                return true;
            }
        }

        return false;
    }
}
```

After refactoring the code in this way I started to work at parser implementation. Firstly, I decided to use `List` data structure representing the Abstract Syntax Tree of my parser. Inside of the `Parser` class there was declared an attribute `List<Statement> parseList` which was going to store each line of code as a statement which contained variable name and its value in direct form like `true` or `false` or as a complex logical expression.

```
public class Parser {
    
    private Lexer lexer;
    private List<Statement> parseList;

    public Parser () {
        lexer = new Lexer();
        parseList = new ArrayList<>();
    }
    ...
```

The `Statement` class has three main attributes: `variableName`, `value` - stores the exact value or the list of expressions defining it, and `expressionOperators` - the list of operators which connect expressions inside of the statement.

```
public class Statement {
    
    private String variableName;
    private List<Expression> value;
    private List<String> expressionOperators;

    public Statement (String variableName) {
        this.variableName = variableName;
        value = new ArrayList<>();
        expressionOperators = new ArrayList<>();
    }
    ...
```

Each expression is also represented by the `Expression` class. It contains string attributes which stores two operands and the operator connecting them. The class has two constructors: one for the situation when the variable has direct value assigned and another one for a complex expression. If the value is direct then it is stored inside the `firstOperand` field. For the sake of simplicity the parser evaluates the expressions of the predefined structure, for example,- `(X AND Y)`.

```
public class Expression {
    
    private String firstOperand;
    private String operator;
    private String secondOperand;

    public Expression (String value) {
        this.firstOperand = value;
    }

    public Expression (String firstOperand, String operator, String secondOperand) {
        this.firstOperand = firstOperand;
        this.operator = operator;
        this.secondOperand = secondOperand;
    }
    ...
```

Finally, it is time to move to the most important method of the `Parser` class,- `fillParseList()`. This method either performs basic semantic analysis, or fills the list with the statements found. Semantic analysis consists in verifying whether the statement contains variable name and declaration.

```
public void fillParseList (String path) {
     List<Token> tokens = this.lexer.evaluate(path);

     int index = 0;
     while (index < tokens.size()) {
         if (tokens.get(index).getCategory().equals(Category.VAR_NAME.toString())) {
             if (tokens.get(index + 1).getCategory().equals(Category.ASSIGNMENT.toString())) {
             ...
```

If the variable has the direct value assigned then the statement has the list of expressions of size one, otherwise the method iterates through the whole expression part of the statement, identifies them, adds to the list and also fills the list of expression operators. The final result of the parser analysis is presented using the `printParserOutput()` method.

```
     if (tokens.get(index + 2).getCategory().equals(Category.VAR_VALUE.toString())) {
         Expression e = new Expression(tokens.get(index + 2).getLiteral());
         List<Expression> value = new ArrayList<>();
         value.add(e);
         s.setValue(value);

         this.parseList.add(s);
         index += 4;
     } else {
         index += 2;

         List<Expression> value = new ArrayList<>();
         List<String> expressionOperators = new ArrayList<>();

         while (!tokens.get(index).getCategory().equals(Category.ENDLINE.toString())) {
             if (tokens.get(index).getCategory().equals(Category.EXPRESSION_BORDER.toString())) {
                 Expression e = new Expression(tokens.get(index + 1).getLiteral(), tokens.get(index + 2).getLiteral(), tokens.get(index + 3).getLiteral());
                 value.add(e);
                 index += 5;
             }

             if (tokens.get(index).getCategory().equals(Category.OPERATOR.toString())) {
                 expressionOperators.add(tokens.get(index).getLiteral());
                 index++;
             }
         }
         ...
```

## Conclusions / Screenshots / Results

Below I will present the sample code which was used for testing the lexer and parser implementations together with the output of the `printParserOutput()` method.

```
x = true;
y=false;
z=(x ANDy)OR (xORy);
```

```
Variable name: x
Value: true

Variable name: y
Value: false

Variable name: z
Expression value: 

Expression 1:
First parameter: x
Operator: AND
Second parameter: y
Connection operator: OR

Expression 2:
First parameter: x
Operator: OR
Second parameter: y
```

In conclusion I would say that by implementing the parser module of my project I finished the study of language syntactic and semantic analysis. Although, the examples were simplified, I grasped the idea of what are lexer and parser and their importance. Speaking about the changes which can be made to the project, I can mention the parser error catching. It is possible to introduce more complex semantic scanning in order to avoid the corresponding errors. It is also possible to make the language itself more difficult, to get rid of the predefined structure in order to study it as a real-world case.

## References

https://www.javatpoint.com/parser

https://www.geeksforgeeks.org/abstract-syntax-tree-ast-in-java/

https://www.javatpoint.com/parse-tree-and-syntax-tree
