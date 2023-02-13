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
        
        while (index < word.length() - 1) {
            endState = existsTransition(state, word.charAt(index));
            if (endState != '-') {
                state = endState;
            } else return false;

            index++;
        }

        endState = existsTransition(state, word.charAt(index)); 
        if (this.F.contains(endState)) {
            return true;
        }

        return false;
    }

    private char existsTransition (char state, char parameter) {
        for (Transition t : delta) {
            if (t.getInitialState() == state  &&  t.getParameter() == parameter) {
                return t.getEndState();
            }
        }
        return '-';
    }
}
