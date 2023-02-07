import java.util.List;
import java.util.Map;
import java.util.Random;

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
        StringBuffer word = new StringBuffer();
        word.append(S);

        Random r = new Random();

        while (!isWord(word.toString())) {
            int nonterm = findNonTerminalChar(word);
            int production = r.nextInt(0, P.get(word.charAt(nonterm)).size());
            word.replace(nonterm, nonterm + 1, P.get(word.charAt(nonterm)).get(production));
        }

        return word.toString();
    }

    private boolean isWord (String arbitrary_word) {
        String copy = arbitrary_word.toLowerCase();

        if (copy.compareTo(arbitrary_word) == 0) {
            return true;
        } else return false;
    }

    private int findNonTerminalChar (StringBuffer sb) {
        for (int i = 0; i < sb.length(); i++) {
            if (Character.isUpperCase(sb.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
}