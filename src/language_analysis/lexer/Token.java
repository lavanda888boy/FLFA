
public class Token {

    private int ID;
    private String category;
    private String literal;

    public Token (int ID, String category, String literal) {
        this.ID = ID;
        this.category = category;
        this.literal = literal;
    }

    public int getID () {
        return this.ID;
    }

    public void setID (int ID) {
        this.ID = ID;
    }

    public String getCategory () {
        return this.category;
    }

    public void setCategory (String category) {
        this.category = category;
    }

    public String getLiteral () {
        return this.literal;
    }

    public void setLiteral (String literal) {
        this.literal = literal;
    }
}

