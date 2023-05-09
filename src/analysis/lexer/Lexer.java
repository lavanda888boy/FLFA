package analysis.lexer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import analysis.lexer.keywords.Category;
import analysis.lexer.keywords.Operator;

public class Lexer {

    public List<Token> evaluate(String path) {
        List<Token> tokens = new ArrayList<>();
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
        String[] lexems = tokenize(text); 

        int counter = 1;
        for (int i = 0; i < lexems.length; i++) {
            if (Operator.contains(lexems[i])) {
                tokens.add(new Token(counter, Category.OPERATOR.toString(), lexems[i]));
            } else if (lexems[i].compareTo("true") == 0  ||  lexems[i].compareTo("false") == 0) {
                tokens.add(new Token(counter, Category.VAR_VALUE.toString(), lexems[i]));
            } else if (lexems[i].compareTo("(") == 0  ||  lexems[i].compareTo(")") == 0) {
                tokens.add(new Token(counter, Category.EXPRESSION_BORDER.toString(), lexems[i]));
            } else if (lexems[i].compareTo("=") == 0) {
                tokens.add(new Token(counter, Category.ASSIGNMENT.toString(), lexems[i]));
            } else if (lexems[i].compareTo(";") == 0) {
                tokens.add(new Token(counter, Category.ENDLINE.toString(), lexems[i]));
            } else if (lexems[i].compareTo(lexems[i].toLowerCase()) == 0) {
                tokens.add(new Token(counter, Category.VAR_NAME.toString(), lexems[i]));
            } else {
                if (lexems[i].compareTo(" ") != 0) {
                    System.out.println("Syntax error in token: " + lexems[i]);
                }
            }

            counter++;
        }
        return tokens;
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
