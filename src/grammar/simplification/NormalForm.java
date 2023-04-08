package grammar.simplification;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class NormalForm implements GrammarSimplification {
    
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
