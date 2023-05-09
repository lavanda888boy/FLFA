package analysis.lexer.keywords;

public enum Operator {
    AND,
    OR, 
    NOT;
    
    public static boolean contains (String token) {
        for (Operator op : values()) {
            if (op.toString().equals(token)) {
                return true;
            }
        }

        return false;
    }
}
