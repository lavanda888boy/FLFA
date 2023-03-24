package analysis.lexer;

public class Lexer {
    
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

        return sb.toString().split(" ");
    }
}
