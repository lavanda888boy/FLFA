package automaton;
import java.util.List;

public class FiniteAutomaton {
    
    private String Q;
    private String sigma_alphabet;
    private List<Transition> beta;
    private char q0;
    private String F;

    public FiniteAutomaton (String Q, String sigma, List<Transition> beta, char q0, String F) {
        this.Q = Q;
        sigma_alphabet = sigma;
        this.beta = beta;
        this.q0 = q0;
        this.F = F;
    }

    public List<Transition> getTransitions () {
        return this.beta;
    }
}
