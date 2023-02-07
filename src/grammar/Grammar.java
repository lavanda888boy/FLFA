package grammar;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import automaton.FiniteAutomaton;
import automaton.Transition;

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

    public FiniteAutomaton toFiniteAutomaton() {
        List<Transition> transitions = new ArrayList<>();

        for (Character state : this.P.keySet()) {
            for (String str : P.get(state)) {
                Transition t;

                if (str.length() == 1) {
                    t = new Transition(state, 'X', str.charAt(0));
                } else {
                    t = new Transition(state, str.charAt(1), str.charAt(0));
                }
                
                transitions.add(t);
            }
        }

        Set<Character> finalStates = new HashSet<>();
        finalStates.add('X');
        for (Character state : this.P.keySet()) {
            if (this.P.get(state).size() == 1  &&  this.P.get(state).get(0).length() == 1) {
                finalStates.add(state);
            }
        }

        return new FiniteAutomaton(V_n + "X", V_t, transitions, this.S, finalStates);
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