package analysis.lexer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Lexer {

    private Set<String> operators;
    
    public Lexer () {
        operators = new HashSet<>(Arrays.asList("AND", "OR", "NOT", "NOR", "NAND", "XOR", "XNOR"));
    }

    public List<Token> evaluate(String path) {
        List<Token> lexems = new ArrayList<>();
        String text = new String();
        
        try {
            File file = new File(path);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                text += sc.nextLine();
            }

            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(text);
        String[] tokens = tokenize(text); 

        int counter = 1;
        for (int i = 0; i < tokens.length; i++) {
            if (this.operators.contains(tokens[i])) {
                lexems.add(new Token(counter, "OPERATOR", tokens[i]));
            } else if (tokens[i].compareTo("true") == 0  ||  tokens[i].compareTo("false") == 0) {
                lexems.add(new Token(counter, "VAR_VALUE", tokens[i]));
            } else if (tokens[i].compareTo("(") == 0  ||  tokens[i].compareTo(")") == 0) {
                lexems.add(new Token(counter, "EXPRESSION_BORDER", tokens[i]));
            } else if (tokens[i].compareTo("=") == 0) {
                lexems.add(new Token(counter, "ASSIGNMENT", tokens[i]));
            } else if (tokens[i].compareTo(";") == 0) {
                lexems.add(new Token(counter, "ENDLINE", tokens[i]));
            } else if (tokens[i].compareTo(tokens[i].toLowerCase()) == 0) {
                lexems.add(new Token(counter, "VAR_NAME", tokens[i]));
            } else {
                if (tokens[i].compareTo(" ") != 0) {
                    System.out.println("Syntax error in token: " + tokens[i]);
                }
            }

            counter++;
        }
        return lexems;
    }
    
    private String[] tokenize(String l) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < l.length() - 1; i++) {
            char curr = l.charAt(i);
            char next = l.charAt(i + 1);

            sb.append(curr);

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
}
