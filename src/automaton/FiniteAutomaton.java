package automaton;
import java.util.List;
import java.util.Set;

public class FiniteAutomaton {
    
    private List<String> Q;
    private String sigma_alphabet;
    private List<Transition> delta;
    private String q0;
    private Set<String> F;

    public FiniteAutomaton (List<String> Q, String sigma, List<Transition> delta, String q0, Set<String> F) {
        this.Q = Q;
        sigma_alphabet = sigma;
        this.delta = delta;
        this.q0 = q0;
        this.F = F;
    }

    public List<String> getQ () {
        return this.Q;
    }

    public String getSigmaAlphabet () {
        return sigma_alphabet;
    }

    public List<Transition> getDelta () {
        return this.delta;
    }

    public String getQ0 () {
        return this.q0;
    }

    public Set<String> getF () {
        return this.F;
    }

    public List<Transition> getTransitions () {
        return this.delta;
    }

    public boolean isDeterministic () {
        if (existsEpsilonTransition()) {
            return false;
        } else {
            Transition curr, next;

            for (int i = 0; i < delta.size() - 1; i++) {
                curr = delta.get(i);
                next = delta.get(i + 1);
                if (curr.getInitialState().compareTo(next.getInitialState()) == 0  &&  curr.getParameter() == next.getParameter()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean existsEpsilonTransition () {
        for (Transition transition : delta) {
            if (transition.getParameter() == 'e') {
                return true;
            }
        }
        return false;
    }

    public boolean wordIsValid (String word) {
        String state = "S";
        int index = 0;
        String endState;

        int marker = 0;
        
        while (index < word.length() - 1) {
            endState = existsTransition(state, word.charAt(index), marker);
            if (endState != "-") {
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

    private String existsTransition (String state, char parameter, int marker) {
        for (Transition t : delta) {
            if (t.getInitialState().compareTo(state) == 0  &&  t.getParameter() == parameter) {
                if (marker == 0) {
                    if (t.getEndState().compareTo("X") == 0) {
                        return "-";
                    }
                }
                return t.getEndState();
            }
        }
        return "-";
    }
}
