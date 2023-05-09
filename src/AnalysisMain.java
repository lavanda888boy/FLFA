import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import analysis.lexer.Lexer;
import analysis.lexer.Token;
import analysis.parser.Parser;

public class AnalysisMain {
    public static void main(String[] args) {
        
        Parser p = new Parser();
        p.fillParseList("resources/code.txt");
        /*
        for (Token token : ts) {
            System.out.println(
                    "ID: " + token.getID() + "; Category: " + token.getCategory() + "; Literal: "
                            + token.getLiteral());
        }*/
    }
}
