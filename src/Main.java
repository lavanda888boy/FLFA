import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import automaton.FiniteAutomaton;
import automaton.Transition;
import grammar.Grammar;

public class Main {
    public static void main(String[] args) {
        
        List<String> Q = new ArrayList<>();
        Q.add("q0");
        Q.add("q1");
        Q.add("q2");
        Q.add("q3");

        String sigma = "abc";

        List<Transition> ts = new ArrayList<>();
        Transition t = new Transition("q0", "q1", 'a');
        ts.add(t);

        t = new Transition("q1", "q2", 'b');
        ts.add(t);

        t = new Transition("q1", "q3", 'b');
        ts.add(t);

        t = new Transition("q2", "q3", 'c');
        ts.add(t);

        t = new Transition("q3", "q3", 'a');
        ts.add(t);

        t = new Transition("q3", "q4", 'b');
        ts.add(t);
    
        String q0 = "q0";

        Set<String> finalStates = new HashSet<>();
        finalStates.add("q4");

        FiniteAutomaton fa = new FiniteAutomaton(Q, sigma, ts, q0, finalStates);
        Grammar g = new Grammar(fa);
        System.out.println(g.getProductions());
        System.out.println(fa.isDeterministic());

        FiniteAutomaton ftest = fa.convertToDFA();
        for (Transition tr : ftest.getDelta()) {
            System.out.println(tr.getInitialState() + " " + tr.getParameter() + " " + tr.getEndState());
        }
        
        /*
        List<String> V_n = new ArrayList<>();
        V_n.add("S");
        V_n.add("R");
        V_n.add("L");
        String V_t = "abcdef";

        Map<String, List<String>> productions = new HashMap<>();

        List<String> p1 = new ArrayList<>();
        p1.add("aS");
        p1.add("bS");
        p1.add("cR");
        p1.add("dL");
        productions.put("S", p1);

        List<String> p2 = new ArrayList<>();

        p2.add("cR");
        p2.add("e");
        productions.put("R", p2);

        List<String> p3 = new ArrayList<>();

        p3.add("fL");
        p3.add("eL");
        p3.add("d");
        productions.put("L", p3);

        String S = "S";

        Grammar grammar = new Grammar(V_n, V_t, productions, S);
        System.out.println(grammar.determineChomskyType());
        */
    }
}
