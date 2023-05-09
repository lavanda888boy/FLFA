package analysis.parser;

import java.util.ArrayList;
import java.util.List;

import analysis.lexer.Lexer;
import analysis.lexer.Token;
import analysis.lexer.keywords.Category;

public class Parser {
    
    private Lexer lexer;
    private List<Statement> parseList;

    public Parser () {
        lexer = new Lexer();
        parseList = new ArrayList<>();
    }

    public List<Statement> getParseList () {
        return this.parseList;
    }

    public void fillParseList (String path) {
        List<Token> tokens = this.lexer.evaluate(path);

        int index = 0;
        while (index < tokens.size()) {
            if (tokens.get(index).getCategory().equals(Category.VAR_NAME.toString())) {
                if (tokens.get(index + 1).getCategory().equals(Category.ASSIGNMENT.toString())) {
                    if (tokens.get(index + 2).getCategory().equals(Category.VAR_VALUE.toString())) {
                        Statement s = new Statement(tokens.get(index).getLiteral());

                        
                    } else {

                    }
                } else {

                }
            } else {

            }
        }
    }
}
