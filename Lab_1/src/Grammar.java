import java.util.List;
import java.util.Map;

public class Grammar {

    private String V_n;
    private String V_t;
    private Map<Character, List<String>> P;
    private char S;

    public Grammar (String V_n, String V_t, Map<Character, List<String>> P, char S) {
        this.V_n = V_n;
        this.V_t = V_t;
        this.P = P;
        this.S = S;
    }

    public Map<Character, List<String>> getProductions () {
        return this.P;
    }

    public String generateString () {
        StringBuffer word = new StringBuffer(S);

        return null;
    }
}