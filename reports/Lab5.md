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

To start with, in this laboratory work I implemented the `parser` module inside of my project. In order to fulfill this task I, first of all, had to modify the `lexer` module. I grouped the keywords and their categories by using Java `enum` data type. It resulted in having two new enums: `Category` and `Operator`.

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

After refactoring the code in this way I started to work at parser implementation. Firstly, I decided to use `List` data structure representing the Abstract Syntax Tree of my parser. Inside of the `Parser` class there was declared an attribute `List<Statement> parseList` which was goig to store each line of code as a statement which contained variable name and its value in direct form like `true` or `false` or as a complex logical expression.

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


## References

https://www.javatpoint.com/parser

https://www.geeksforgeeks.org/abstract-syntax-tree-ast-in-java/

https://www.javatpoint.com/parse-tree-and-syntax-tree
