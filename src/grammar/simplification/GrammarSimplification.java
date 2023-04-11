package grammar.simplification;

import java.util.List;
import java.util.Map;

import grammar.Grammar;

public interface GrammarSimplification {
    
    void eliminateE_Productions (Map<String, List<String>> productions);

    void eliminateUnitProductions (Map<String, List<String>> productions);

    void eliminateNonProductiveSymbols (Grammar g);

    void eliminateInaccesibleSymbols (Grammar g);
}
