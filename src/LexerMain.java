import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import analysis.lexer.Lexer;
import analysis.lexer.Token;

public class LexerMain {
    public static void main(String[] args) {
        if (args.length > 0) {
            Pattern pattern = Pattern.compile(".txt");
            Matcher matcher = pattern.matcher(args[0]);

            if (matcher.find()) {
                Lexer l = new Lexer();
                List<Token> ts = l.analyze(args[0]);

                for (Token token : ts) {
                    System.out.println(
                            "ID: " + token.getID() + "; Category: " + token.getCategory() + "; Literal: "
                                    + token.getLiteral());
                }
            } else {
                System.out.println("No file provided");
            }
        } else {
            System.out.println("Requires at least one more argument");
        }
    }
}
