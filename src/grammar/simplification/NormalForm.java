package grammar.simplification;

import java.util.Map;
import java.util.Set;

import grammar.Grammar;

import java.util.HashSet;
import java.util.List;

public class NormalForm implements GrammarSimplification {

    @Override
    public void eliminateE_Productions (Map<String, List<String>> productions) {
        Set<String> eSymbols = new HashSet<>();

        for (String symbol : productions.keySet()) {
            for (String production : productions.get(symbol)) {
                if (production.compareTo("e") == 0) {
                    eSymbols.add(symbol);
                    productions.get(symbol).remove(production);
                    break;
                }
            }
        }

        for (String symbol : eSymbols) {
            for (List<String> production : productions.values()) {
                for (String result : production) {
                    if (result.contains(symbol)) {
                        
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
    public void eliminateInaccesibleSymbols (Map<String, List<String>> productions) {
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
            
            if (checker == 0) {
                inaccesibleSymbols.add(symbol);
            }
        }

        for (String inSymbol : inaccesibleSymbols) {
            productions.remove(inSymbol);
        }
    }
}
