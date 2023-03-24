package analysis.lexer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lexer {

    public List<Token> analyze(String path) {
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
            //String token = tokens[i].toLowerCase();
            System.out.println(tokens[i]);

            counter++;
        }
        return lexems;
    }
    
    public String[] tokenize(String l) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < l.length() - 1; i++) {
            char curr = l.charAt(i);
            char next = l.charAt(i + 1);

            sb.append(curr);

            if (Character.isLetter(curr) && !Character.isLetter(next) && next != ' ') {
                sb.append(" ");
            } else if (curr == '=' || curr == '(' || curr == ')') {
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
