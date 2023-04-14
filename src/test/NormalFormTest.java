package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import grammar.Grammar;
import grammar.simplification.NormalForm;

public class NormalFormTest {

    private Grammar grammar;
    private NormalForm nf;
    
    @Before
    public void setup () {
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

        grammar = new Grammar(V_n, V_t, productions, S);
        nf = new NormalForm();
    }


    @Test
    public void epsilonEliminationTest () {
        
    }


    @Test
    public void unitProductionsTest () {
        
    }


    @Test
    public void nonProductiveSymbolsTest () {
        
    }


    @Test
    public void inaccesibleSymbolsTest () {
        
    }


    @Test
    public void chomskySimplificationTest () {
        
    }
}
