package analysis;

import analysis.lexer.Lexer;

public class LexerTest {
    public static void main(String[] args) {
        String expression = "x = true;z=false; y= (z ANDxy)ORm";
        expression = expression.replaceAll("\\s", "");

        Lexer lexer = new Lexer();
        String[] tokens = lexer.tokenize(expression);

        for (int i = 0; i < tokens.length; i++) {
            System.out.println(tokens[i]);
        }
    }
}
