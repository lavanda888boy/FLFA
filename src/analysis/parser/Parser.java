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
                    Statement s = new Statement(tokens.get(index).getLiteral());

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

                        s.setValue(value);
                        s.setExpressionOperators(expressionOperators);
                        this.parseList.add(s);
                        index++;
                    }
                } else {
                    System.out.println("Parsing error: Variable assignment missing!");
                    System.out.println("Error in the token " + tokens.get(index).getLiteral());
                    break;
                }
            } else {
                System.out.println("Parsing error: Variable declaration missing!");
                System.out.println("Error in the token: " + tokens.get(index).getLiteral());
                break;
            }
        }
    }
}
