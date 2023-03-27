# Lexer & Scanner

### Course: Formal Languages & Finite Automata
### Author: Bajenov Sevastian

----

## Theory

* `Lexical analysis` is the first phase of a compiler. It takes modified source code from language preprocessors that are written in the form of sentences. The lexical analyzer breaks these syntaxes into a series of tokens, by removing any whitespace or comments in the source code. If the lexical analyzer finds a token invalid, it generates an error.

* A `Scanner` is a part of the lexer or lexical analyzer module which splits the input string into lexemes according to their logical meaning.

* An `Evaluator` is another part of the lexer which assigns particular categories to each of the obtained by the scanner lexemes.

* A `lexeme` is a sequence of characters in the source program that matches the pattern for a token and is identified by the lexical analyzer as an instance of that token.

* A lexical `token` is a sequence of characters that can be treated as a unit in the grammar of the programming languages.

## Objectives:

1. Understand what lexical analysis is.
2. Get familiar with the inner workings of a lexer/scanner/tokenizer.
3. Implement a sample lexer and show how it works.

## Implementation description

The lexical analyzer in this laboratory work is based on a simplified logical expressions' language. Basically, it permits the user to create a boolean variable and assign to it a value or a logical expression (it may be complex, containing different operations: `AND`, `OR`, `NAND`, `NOR`, `XOR`, `XNOR`, `NOT`). In the next section an example of code will be presented. The code is being read from a .txt file, which is situated in the `resources` folder.

For implementing lexical analyzer in my project I created two hierarchical folder inside the `src`
folder called `analysis/lexer`. The classes `Lexer` and `Token` are situated inside the `lexer` folder. 

`Token` class contains three main attributes: `ID`, `category` and `literal`. Category is assigned to a token depending on its role in the code (OPERATOR, for instance). ID will be counted iteratively and the literal will be actually the 'word' found in the input string, related to the corresponding token.

```
public class Token {

    private int ID;
    private String category;
    private String literal;

    public Token (int ID, String category, String literal) {
        this.ID = ID;
        this.category = category;
        this.literal = literal;
    }
    ...
```

`Lexer` class is based in two methods: `tokenize()` and `evaluate()`. 

The first one represents the `scanner` module of the lexer. It splits the input string into lexemes, according to the terminal symbols found (curly brackets for instance). The spaces are being inserted after each of the suitable symbols and then the built-in method `split()` is called on the resulting string.

```
private String[] tokenize(String l) {
        
    ...

        if (Character.isLetter(curr) && !Character.isLetter(next) && next != ' ') {
            sb.append(" ");
        } else if (curr == '(' || curr == ')') {
            sb.append(" ");
        } else if (curr == '=' && next != ' ') {
            sb.append(" ");
        } else if ((Character.isUpperCase(curr) && Character.isLowerCase(next)) 
            || (Character.isUpperCase(next) && Character.isLowerCase(curr))) {
            sb.append(" ");
        } else if (curr == ';') {
            sb.append(" ");
        }
    }
    sb.append(l.charAt(l.length() - 1));
    System.out.println(sb.toString());

    return sb.toString().split(" ");
}
```

The second one plays the role of the `evaluator`. According to some strictly defined rules it assigns category to each token. Within this process it is making use of the set `operators` which contains keywords of the language. If the token's lexeme does not fit any of the rules then a syntax error is being reported.

```
public List<Token> evaluate(String path) {
    String[] lexems = tokenize(text); 

    int counter = 1;
    for (int i = 0; i < lexems.length; i++) {
        if (this.operators.contains(lexems[i])) {
            tokens.add(new Token(counter, "OPERATOR", lexems[i]));
        } else if (lexems[i].compareTo("true") == 0  ||  lexems[i].compareTo("false") == 0) {
            tokens.add(new Token(counter, "VAR_VALUE", lexems[i]));
        } else if (lexems[i].compareTo("(") == 0  ||  lexems[i].compareTo(")") == 0) {
            tokens.add(new Token(counter, "EXPRESSION_BORDER", lexems[i]));
        } else if (lexems[i].compareTo("=") == 0) {
            tokens.add(new Token(counter, "ASSIGNMENT", lexems[i]));
        } else if (lexems[i].compareTo(";") == 0) {
            tokens.add(new Token(counter, "ENDLINE", lexems[i]));
        } else if (lexems[i].compareTo(lexems[i].toLowerCase()) == 0) {
            tokens.add(new Token(counter, "VAR_NAME", lexems[i]));
        } else {
            if (lexems[i].compareTo(" ") != 0) {
                System.out.println("Syntax error in token: " + lexems[i]);
            }
        }

        counter++;
    }
    return tokens;
}
```

The scanner and evaluator are bound together so that in the main class `LexerMain`, the method `evaluate()` is being called which, on its own, calls `tokenize()`;


## Conclusions / Screenshots / Results

Below you can see an example of code:
```
x = true;
y=falseee;
z=(x ANDy)OR (xORy);
```

And the output of the lexer:
```
Initial string: x = true;y=falseee;z=(x ANDy)OR (xORy);

String after space insertion: x = true ; y = falseee ; z = ( x AND y ) OR ( x OR y ) ;

List of tokens:
ID: 1; Category: VAR_NAME; Literal: x
ID: 2; Category: ASSIGNMENT; Literal: =
ID: 3; Category: VAR_VALUE; Literal: true
ID: 4; Category: ENDLINE; Literal: ;
ID: 5; Category: VAR_NAME; Literal: y
ID: 6; Category: ASSIGNMENT; Literal: =
ID: 7; Category: VAR_NAME; Literal: falseee
ID: 8; Category: ENDLINE; Literal: ;
ID: 9; Category: VAR_NAME; Literal: z
ID: 10; Category: ASSIGNMENT; Literal: =
ID: 11; Category: EXPRESSION_BORDER; Literal: (
ID: 12; Category: VAR_NAME; Literal: x
ID: 13; Category: OPERATOR; Literal: AND
ID: 14; Category: VAR_NAME; Literal: y
ID: 15; Category: EXPRESSION_BORDER; Literal: )
ID: 16; Category: OPERATOR; Literal: OR
ID: 17; Category: EXPRESSION_BORDER; Literal: (
ID: 18; Category: VAR_NAME; Literal: x
ID: 19; Category: OPERATOR; Literal: OR
ID: 20; Category: VAR_NAME; Literal: y
ID: 21; Category: EXPRESSION_BORDER; Literal: )
ID: 22; Category: ENDLINE; Literal: ;
```

It is possible to notice that the lexeme `falseee` was detected as variable name, though it is a wrong-defined variable value. This improper output of the lexer, in my opinion, will be handled further by the parser, which will notice such anomalies and produce the corresponding output. In this laboratory work I learned how to split simple language into tokens of certain categories; I think that in the next laboratory work, if I have to implement the parser of the language, I will be ready to do it by having the proper output from the lexer. It will help me build the logical connection between all the tokens.

## References
https://en.wikipedia.org/wiki/Lexical_analysis

https://www.geeksforgeeks.org/introduction-of-lexical-analysis/

https://www.tutorialspoint.com/compiler_design/compiler_design_lexical_analysis.htm
