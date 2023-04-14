package grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import automaton.FiniteAutomaton;
import automaton.Transition;

public class Grammar {

    private List<String> V_n;
    private String V_t;
    private Map<String, List<String>> P;
    private String S;

    public Grammar (List<String> V_n, String V_t, Map<String, List<String>> P, String S) {
        this.V_n = V_n;
        this.V_t = V_t;
        this.P = P;
        this.S = S;
    }
 
    public Grammar (FiniteAutomaton fa) {
        this(fa.getQ(), fa.getSigmaAlphabet(), null, fa.getQ0());

        List<Transition> transitions = fa.getTransitions();
        Map<String, List<String>> productions = new HashMap<>();

        String st, endst;
        char param;

        for (Transition transition : transitions) {
            st = transition.getInitialState();
            endst = transition.getEndState();
            param = transition.getParameter();

            if (productions.containsKey(transition.getInitialState())) {
                productions.get(st).add(param + endst);
            } else {
                List<String> endStates = new ArrayList<>();
                endStates.add(param + endst);
                productions.put(st, endStates);
            }
        }

        List<String> boofer_states;
        for (String fst : fa.getF()) {
            boofer_states = new ArrayList<>();
            boofer_states.add("e");
            productions.put(fst, boofer_states);
        }

        this.P = productions;
    }

    public List<String> getNonTerminals () {
        return this.V_n;
    }

    public Map<String, List<String>> getProductions () {
        return this.P;
    }

    public void setProductions (Map<String, List<String>> productions) {
        this.P = productions;
    }

    public void setStartingSymbol (String symbol) {
        this.S = symbol;
    }


    public void showProductions () {
        for (String symbol : this.P.keySet()) {
            System.out.print(symbol + ": ");
            for (String production : this.P.get(symbol)) {
                System.out.print(production + " | ");
            }
            System.out.println();
        }
        System.out.println("\n\n");
    }
    

    public void substituteStartingSymbol () {
        byte marker = 0;

        for (List<String> prods : this.P.values()) {
            for (String production : prods) {
                if (production.contains("S")) {
                    this.V_n.add("S1");
                    this.setStartingSymbol("S1");
                    marker++;
                    break;
                }
            }

            if (marker > 0) {
                this.P.put("S1", new ArrayList<>());
                this.P.get("S1").add("S");
                return;
            }
        }
    }


    public String generateString () {
        StringBuffer word = new StringBuffer();
        word.append(S);

        Random r = new Random();

        while (!isWord(word.toString())) {
            int nonterm = findNonTerminalChar(word);
            int production = r.nextInt(P.get(Character.toString(word.charAt(nonterm))).size());
            word.replace(nonterm, nonterm + 1, P.get(Character.toString(word.charAt(nonterm))).get(production));
        }

        return word.toString();
    }


    public FiniteAutomaton toFiniteAutomaton() {
        List<Transition> transitions = new ArrayList<>();

        for (String state : this.P.keySet()) {
            for (String str : P.get(state)) {
                Transition t;

                if (str.length() == 1) {
                    t = new Transition(state, "X", str.charAt(0));
                } else {
                    t = new Transition(state, Character.toString(str.charAt(1)), str.charAt(0));
                }
                
                transitions.add(t);
            }
        }

        Set<String> finalStates = new HashSet<>();
        finalStates.add("X");
        for (String state : this.P.keySet()) {
            if (this.P.get(state).size() == 1  &&  this.P.get(state).get(0).length() == 1) {
                finalStates.add(state);
            }
        }

        List<String> Q = V_n;
        Q.add("X");

        return new FiniteAutomaton(Q, V_t, transitions, this.S, finalStates);
    }


    public int determineChomskyType () {
        if (checkThirdType()) {
            return 3;
        } else if (checkSecondType()) {
            return 2;
        } else if (checkFirstType()) {
            return 1;
        } else {
            return 0;
        }
    }


    private boolean checkThirdType () {
        List<String> productions;
        for (String state : this.P.keySet()) {
            if (state.length() > 1) {
                return false;
            }

            productions = this.P.get(state);
            for (String prod : productions) {
                if (prod.length() > 2) {
                    return false;
                } else if (prod.length() == 2  &&  (Character.isUpperCase(prod.charAt(0)) || Character.isLowerCase(prod.charAt(1)))) {
                    return false;
                } else if (prod.length() == 1  &&  Character.isUpperCase(prod.charAt(0))) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean checkSecondType () {
        for (String state : this.P.keySet()) {
            if (state.length() > 1) {
                return false;
            }
        }
        return true;
    }


    private boolean checkFirstType () {
        List<String> productions;
        for (String state : this.P.keySet()) {
            productions = this.P.get(state);

            for (String prod : productions) {
                if ((state.length() > prod.length())  ||  prod.compareTo("e") == 0) {
                    return false;
                }
            }
        }
        return true;
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