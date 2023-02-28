import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

        String sigma = "acb";

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
    }
}
