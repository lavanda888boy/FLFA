package grammar.simplification;

import java.util.List;
import java.util.Map;

public interface GrammarSimplification {
    
    //void eliminateE_Productions (Map<String, List<String>> productions);

    //void eliminateUnitProductions (Map<String, List<String>> productions);

    //void eliminateNonProductiveSymbols (Map<String, List<String>> productions);

    void eliminateInaccesibleSymbols (Map<String, List<String>> productions);
}
