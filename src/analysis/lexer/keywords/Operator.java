package analysis.lexer.keywords;

public enum Operator {
    AND,
    OR, 
    NOT, 
    NOR, 
    NAND, 
    XOR, 
    XNOR;

    public static boolean contains (String token) {
        for (Operator op : values()) {
            if (op.toString().equals(token)) {
                return true;
            }
        }

        return false;
    }
}
