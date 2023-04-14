package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
        nf.eliminateE_Productions(grammar.getProductions());

        assertEquals("The number of productions for A does not match!", 8, grammar.getProductions().get("A").size());
        assertFalse("E-production for A was not removed", grammar.getProductions().get("A").contains("e"));
        assertEquals("The number of productions for S does not match!", 3, grammar.getProductions().get("S").size());
    }


    @Test
    public void unitProductionsTest () {
        nf.eliminateUnitProductions(grammar.getProductions());

        assertEquals("The number of productions for A does not match!", 6, grammar.getProductions().get("A").size());
        assertFalse("Unit production for A was not removed", grammar.getProductions().get("A").contains("B"));
    }


    @Test
    public void nonProductiveSymbolsTest () {
        nf.eliminateNonProductiveSymbols(grammar);

        assertEquals("The number of nonterminals does not match!", 3, grammar.getProductions().keySet().size());
        assertNull("Nonproductive symbol D was not removed!", grammar.getProductions().get("D"));
        assertNull("Nonproductive symbol C was not removed!", grammar.getProductions().get("C"));
    }


    @Test
    public void inaccesibleSymbolsTest () {
        nf.eliminateInaccesibleSymbols(grammar);

        assertEquals("The number of nonterminals does not match!", 4, grammar.getProductions().keySet().size());
        assertNull("Inaccesible symbol C was not removed!", grammar.getProductions().get("C"));
    }


    @Test
    public void chomskySimplificationTest () {
        nf.normalizeChomsky(grammar);

        assertEquals("The number of nonterminals does not match!", 8, grammar.getProductions().keySet().size());
        assertEquals("Wrong production for nonterminal G", "a", grammar.getProductions().get("G").get(0));
        assertEquals("Wrong production for nonterminal E", "AB", grammar.getProductions().get("E").get(0));
    }
}
