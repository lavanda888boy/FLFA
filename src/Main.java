import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import automaton.FiniteAutomaton;
import automaton.Transition;
import grammar.Grammar;
import grammar.simplification.NormalForm;

public class Main {
    public static void main(String[] args) {
        
        /*
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
        */
        
        
        List<String> V_n = new ArrayList<>();
        V_n.addAll(Arrays.asList("S", "A", "B", "C", "D"));
        String V_t = "ab";

        Map<String, List<String>> productions = new HashMap<>();

        List<String> p1 = new ArrayList<>();
        p1.addAll(Arrays.asList("aB", "bA"));
        productions.put("S", p1);

        List<String> p2 = new ArrayList<>();
        p2.addAll(Arrays.asList("B", "b", "aD", "AS", "bAAB", "e"));
        productions.put("A", p2);

        List<String> p3 = new ArrayList<>();
        p3.addAll(Arrays.asList("b", "bS"));
        productions.put("B", p3);

        List<String> p4 = new ArrayList<>();
        p4.addAll(Arrays.asList("AB"));
        productions.put("C", p4);

        List<String> p5 = new ArrayList<>();
        p5.addAll(Arrays.asList("BB"));
        productions.put("D", p5);


        String S = "S";

        Grammar grammar = new Grammar(V_n, V_t, productions, S);
        
        /* 
        List<String> V_n = new ArrayList<>();
        V_n.addAll(Arrays.asList("S", "T", "R"));
        String V_t = "01";

        Map<String, List<String>> productions = new HashMap<>();

        List<String> p1 = new ArrayList<>();
        p1.addAll(Arrays.asList("0S1", "1S0S", "T"));
        productions.put("S", p1);

        List<String> p2 = new ArrayList<>();
        p2.addAll(Arrays.asList("S", "R", "e"));
        productions.put("T", p2);

        List<String> p3 = new ArrayList<>();
        p3.addAll(Arrays.asList("0SR"));
        productions.put("R", p3);

        String S = "S";

        Grammar grammar = new Grammar(V_n, V_t, productions, S);
        */

        grammar.showProductions();
        System.out.println();
        
        NormalForm nf = new NormalForm();
        //nf.eliminateInaccesibleSymbols(grammar.getProductions());
        nf.eliminateE_Productions(grammar.getProductions());
        grammar.showProductions();
        System.out.println();

        nf.eliminateUnitProductions(grammar.getProductions());
        //nf.eliminateNonProductiveSymbols(grammar);
        grammar.showProductions();
    }
}
