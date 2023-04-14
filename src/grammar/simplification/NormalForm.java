package grammar.simplification;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import grammar.Grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class NormalForm implements GrammarSimplification {

    public void normalizeChomsky (Grammar g) {
        g.substituteStartingSymbol();

        this.eliminateE_Productions(g.getProductions());
        this.eliminateUnitProductions(g.getProductions());
        this.eliminateNonProductiveSymbols(g);
        this.eliminateInaccesibleSymbols(g);

        Map<String, List<String>> updatedProductions = new HashMap<>();
        Map<String, List<String>> newNonTerminals = new HashMap<>();

        for (String symbol : g.getProductions().keySet()) {
            List<String> updatedList = new ArrayList<>();

            for (String result : g.getProductions().get(symbol)) {
                int current;
                
                while (!result.matches("[a-z]|[A-Z][A-Z]")) {
                    current = 0;
                    String token = "";
                    
                    while (current + 1 < result.length()) {
                        token = result.substring(current, current + 2);

                        if (token.matches("[a-z][A-Z]")) {
                            String substitution = findExistingNonTerm(newNonTerminals, token.substring(0, 1));
                            
                            if (substitution == null) {
                                substitution = getNewNonTerminal(g.getNonTerminals());
                                newNonTerminals.put(substitution, new ArrayList<>(Arrays.asList(token.substring(0, 1))));
                            }

                            result = result.replace(token.substring(0, 1), substitution);
                        } else {
                            String substitution = findExistingNonTerm(newNonTerminals, token);
                            
                            if (substitution == null) {
                                substitution = getNewNonTerminal(g.getNonTerminals());
                                newNonTerminals.put(substitution, new ArrayList<>(Arrays.asList(token)));
                            }

                            result = result.replace(token, substitution);
                        }

                        current += 2;
                    }
                }
                updatedList.add(result);
            }
            updatedProductions.put(symbol, updatedList);
        }
        g.setProductions(updatedProductions);

        for (String s : newNonTerminals.keySet()) {
            g.getProductions().put(s, newNonTerminals.get(s));
        }
    }


    private String findExistingNonTerm (Map<String, List<String>> newNonTerms, String prod) {
        for (String symbol : newNonTerms.keySet()) {
            if (newNonTerms.get(symbol).get(0).compareTo(prod) == 0) {
                return symbol;
            }
        }

        return null;
    }


    private String getNewNonTerminal (List<String> nonTerms) {
        char init = 'A';

        while (init <= 'Z') {
            if (!nonTerms.contains(Character.toString(init))) {
                nonTerms.add(Character.toString(init));
                return Character.toString(init);
            } else {
                init++;
            }
        }

        return "*";
    }


    @Override
    public void eliminateE_Productions (Map<String, List<String>> productions) {
        Queue<String> eSymbols = new LinkedList<>();
        Set<String> visitedSymbols = new HashSet<>();

        analyzeNullables(eSymbols, productions, visitedSymbols);

        while (!eSymbols.isEmpty()) {
            String eSymbol = eSymbols.poll();
            visitedSymbols.add(eSymbol);
            long counter;

            for (List<String> prods : productions.values()) {
                List<String> prodsCopy = new ArrayList<>(prods);

                for (String production : prods) {
                    if (production.contains(eSymbol)) {
                        String boofer = production.replace(eSymbol, "");
                        
                        if (boofer.compareTo("") == 0) {
                            prodsCopy.add("e");
                        } else {
                            prodsCopy.add(boofer);
                        }

                        counter = production.chars().filter(ch -> ch == eSymbol.charAt(0)).count();
                        
                        if (counter > 1) {
                            for (int i = 1; i < Math.pow(2, counter) - 1; i++) {
                                String combination = Integer.toBinaryString(i);
                                int diff = (int) counter - combination.length();
    
                                if (diff != 0) {
                                    for (int j = 0; j < diff; j++) {
                                        combination = "0" + combination;
                                    }
                                }
    
                                StringBuilder sb = new StringBuilder();
                                int index = 0;
                                int k = 0;    
                                
                                while (k < production.length()) {
                                    if (production.charAt(k) == eSymbol.charAt(0)) {
                                        if (combination.charAt(index) == '1') {
                                            sb.append(production.charAt(k));
                                        }

                                        index++;
                                    } else if (production.charAt(k) != eSymbol.charAt(0)) {
                                        sb.append(production.charAt(k));
                                    }
                                    k++;
                                }

                                if (!prodsCopy.contains(sb.toString())) {
                                    prodsCopy.add(sb.toString());
                                }
                            }
                        }
                    }
                }

                prods.clear();
                prods.addAll(prodsCopy);
            }

            analyzeNullables(eSymbols, productions, visitedSymbols);

            for (String symbol : visitedSymbols) {
                productions.get(symbol).removeIf(production -> production.compareTo("e") == 0);
            }
        }
    }


    private void analyzeNullables (Queue<String> nullables, Map<String, List<String>> productions, Set<String> visited) {
        for (String symbol : productions.keySet()) {
            for (String production : productions.get(symbol)) {
                if ((production.compareTo("e") == 0 || containsOnlyNullables(nullables, productions, symbol))
                    && !visited.contains(symbol)) {
                    nullables.add(symbol);
                    productions.get(symbol).remove(production);
                    break;
                }
            }
        }
    }


    private boolean containsOnlyNullables (Queue<String> nullables, Map<String, List<String>> productions, String symbol) {
        for (String production : productions.get(symbol)) {
            int count = 0;

            for (int i = 0; i < production.length(); i++) {
                if (!nullables.contains(Character.toString(production.charAt(i)))) {
                    count++;
                }
            }

            if (count == 0) {
                return true;
            }
        }

        return false;
    }


    @Override
    public void eliminateUnitProductions (Map<String, List<String>> productions) {
        int unitMarker = 1;

        while (unitMarker != 0) {
            unitMarker = 0;

            for (String symbol : productions.keySet()) {
                Set<String> lineUnits = productions.get(symbol)
                                                    .stream()
                                                    .filter(production -> production.length() == 1 && Character.isUpperCase(production.charAt(0)))
                                                    .collect(Collectors.toSet());
                if (!lineUnits.isEmpty()) {
                    productions.get(symbol).removeIf(production -> production.length() == 1 && Character.isUpperCase(production.charAt(0)));
                    unitMarker++;
                    
                    for (String unit : lineUnits) {
                        for (String prod : productions.get(unit)) {
                            if (!productions.get(symbol).contains(prod)) {
                                productions.get(symbol).add(prod);
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void eliminateNonProductiveSymbols (Grammar g) {
        Map<String, List<String>> productions = g.getProductions();
        Set<String> productiveSymbols = new HashSet<>();

        for (String symbol : productions.keySet()) {
            for (String production : productions.get(symbol)) {
                if ((production.length() == 1 && Character.isLowerCase(production.charAt(0)))
                    || isProductive(production, productiveSymbols)) {
                    productiveSymbols.add(symbol);
                    break;
                }
            }
        }

        Set<String> nonTerminals = new HashSet<>(g.getNonTerminals());
        nonTerminals.removeAll(productiveSymbols);
        Set<String> nonProductiveSymbols = nonTerminals;
        
        for (String symbol : nonProductiveSymbols) {
            productions.remove(symbol);

            for (List<String> prods : productions.values()) {
                prods.removeIf(production -> production.contains(symbol));
            }
        }
    }


    private boolean isProductive (String production, Set<String> prodSymbols) {
        byte noticedProductive = 0;
        byte noticedTerminal = 0;

        for (int i = 0; i < production.length(); i++) {
            if (prodSymbols.contains(Character.toString(production.charAt(i)))) {
                noticedProductive++;
            } else if (Character.isLowerCase(production.charAt(i))) {
                noticedTerminal++;
            }

            if (noticedProductive == 1  &&  noticedTerminal == 1) {
                return true;
            }
        }

        return false;
    }

    
    @Override
    public void eliminateInaccesibleSymbols (Grammar g) {
        Map<String, List<String>> productions = g.getProductions();
        Set<String> inaccesibleSymbols = new HashSet<>();
        byte checker;

        for (String symbol : productions.keySet()) {
            checker = 0;

            for (List<String> production : productions.values()) {
                for (String result : production) {
                    if (result.contains(symbol)) {
                        checker += 1;
                        break;
                    }
                }
            }
            
            if (checker == 0  &&  !symbol.contains("S")) {
                inaccesibleSymbols.add(symbol);
            }
        }

        for (String inSymbol : inaccesibleSymbols) {
            productions.remove(inSymbol);
            g.getNonTerminals().removeIf(nonTerm -> nonTerm.compareTo(inSymbol) == 0);
        }
    }
}
