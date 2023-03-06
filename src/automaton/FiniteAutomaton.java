package automaton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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


    private boolean existsEpsilonTransition () {
        for (Transition transition : delta) {
            if (transition.getParameter() == 'e') {
                return true;
            }
        }
        return false;
    }


    public FiniteAutomaton convertToDFA () {
        Set<String> visited_states = new HashSet<>();
        Queue<String> appeared_states = new LinkedList<>();
        List<String[]> state_table = new LinkedList<>();

        int counter = 0;
        do {
            String[] line = new String[sigma_alphabet.length() + 1];
            
            if (counter == 0) {
                line[0] = "q0";
                counter++;
            } else {
                line[0] = appeared_states.poll();
            }

            String[] component_states = line[0].split(", ");

            for (int i = 1; i < line.length; i++) {
                Set<String> composite_state = new HashSet<>();
        
                for (int j = 0; j < component_states.length; j++) {
                    Set<String> s = new HashSet<>();
                    
                    for (Transition transition : delta) {
                        if (transition.getInitialState().compareTo(component_states[j]) == 0  &&  transition.getParameter() == sigma_alphabet.charAt(i - 1)) {
                            s.add(transition.getEndState());
                        }
                    }
                    composite_state.addAll(s);
                }

                if (composite_state.size() != 0) {
                    String resulting_compoString = String.join(", ", composite_state);

                    if (!visited_states.contains(resulting_compoString) && resulting_compoString.compareTo("") != 0) {
                        visited_states.add(resulting_compoString);
                        appeared_states.add(resulting_compoString);
                    }

                    line[i] = resulting_compoString;
                }
            }
            state_table.add(line);
        } while (!appeared_states.isEmpty());

        List<String> qList = new ArrayList<>(visited_states);
        Set<String> newF = new HashSet<>();

        for (String vState : visited_states) {
            for (String fState : F) {
                if (vState.contains(fState)) {
                    newF.add(vState);
                    break;
                }
            }
        }

        List<Transition> newDelta = new ArrayList<>();
        Transition t;
        for (String[] line : state_table) {
            for (int index = 1; index < line.length; index++) {
                if (line[index] != null) {
                    t = new Transition(line[0], line[index], sigma_alphabet.charAt(index - 1));
                    newDelta.add(t);
                }
            }
        }
        return new FiniteAutomaton(qList, sigma_alphabet, newDelta, "q0", newF);
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
