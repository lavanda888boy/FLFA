package automaton;
import java.util.List;
import java.util.Set;

public class FiniteAutomaton {
    
    private String Q;
    private String sigma_alphabet;
    private List<Transition> delta;
    private char q0;
    private Set F;

    public FiniteAutomaton (String Q, String sigma, List<Transition> delta, char q0, Set F) {
        this.Q = Q;
        sigma_alphabet = sigma;
        this.delta = delta;
        this.q0 = q0;
        this.F = F;
    }

    public List<Transition> getTransitions () {
        return this.delta;
    }

    public boolean wordIsValid (String word) {
        char state = 'S';
        int index = 0;
        char endState;

        int marker = 0;
        
        while (index < word.length() - 1) {
            endState = existsTransition(state, word.charAt(index), marker);
            if (endState != '-') {
                state = endState;
            } else return false;

            index++;
        }

        marker = 1;
        endState = existsTransition(state, word.charAt(index), marker); 
        if (this.F.contains(endState)) {
            return true;
        }

        return false;
    }

    private char existsTransition (char state, char parameter, int marker) {
        for (Transition t : delta) {
            if (t.getInitialState() == state  &&  t.getParameter() == parameter) {
                if (marker == 0) {
                    if (t.getEndState() == 'X') {
                        return '-';
                    }
                }
                return t.getEndState();
            }
        }
        return '-';
    }
}
