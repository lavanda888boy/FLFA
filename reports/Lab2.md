# Determinism in Finite Automata. Conversion from NDFA to DFA. Chomsky Hierarchy.

### Course: Formal Languages & Finite Automata
### Author: Bajenov Sevastian

----

## Theory
`Chomsky hierarchy` is the set of rules dividing the grammars into four distinct categories.

* `Type 0` grammars generate recursively enumerable languages. The productions have no restrictions. They are any phase structure grammar including all formal grammars. They generate the languages that are recognized by a Turing machine. The productions can be in the form of α → β where α is a string of terminals and nonterminals with at least one non-terminal and α cannot be null. β is a string of terminals and non-terminals.

* `Type 1` grammars generate context-sensitive languages. The productions must be in the form α A β → α γ β, where A ∈ N (Non-terminal) and α, β, γ ∈ (T ∪ N)* (Strings of terminals and non-terminals). The strings α and β may be empty, but γ must be non-empty. The rule S → ε is allowed if S does not appear on the right side of any rule.

* `Type 2` grammars generate context-free languages. The productions must be in the form A → γ, where A ∈ N (Non terminal) and γ ∈ (T ∪ N)* (String of terminals and non-terminals).

* `Type 3` grammars generate regular languages. Type-3 grammars must have a single non-terminal on the left-hand side and a right-hand side consisting of a single terminal or single terminal followed by a single non-terminal. The productions must be in the form X → a or X → aY, where X, Y ∈ N (Non terminal) and a ∈ T (Terminal). The rule S → ε is allowed if S does not appear on the right side of any rule.

In `DFA` (deterministic FA), there is exactly one transition from any state of finite automata for each input symbol. It accepts the string by halting at a final state, and it rejects it by halting at a non-final state.

In `NFA` (non-deterministic FA), there are zero or more transitions from any state of finite automata for each input symbol (or if there are e-transitions).

## Objectives:

1. Understand what an automaton is and what it can be used for.

2. Continuing the work in the same repository and the same project, the following need to be added:
    a. Provide a function in your grammar type/class that could classify the grammar based on Chomsky hierarchy.

    b. For this you can use the variant from the previous lab.

3. According to your variant number (by universal convention it is register ID), get the finite automaton definition and do the following tasks:

    a. Implement conversion of a finite automaton to a regular grammar.

    b. Determine whether your FA is deterministic or non-deterministic.

    c. Implement some functionality that would convert an NDFA to a DFA.
    
    d. Represent the finite automaton graphically (Optional, and can be considered as a __*bonus point*__):
      
    - You can use external libraries, tools or APIs to generate the figures/diagrams.
        
    - Your program needs to gather and send the data about the automaton and the lib/tool/API return the visual representation.


## Implementation description

* In this laboratory work I was working on the following automaton Q = {q0,q1,q2,q3,q4}, ∑ = {a,b,c}, F = {q4}, δ(q0,a) = q1, δ(q1,b) = q2, δ(q1,b) = q3, δ(q2,c) = q3, δ(q3,a) = q3, δ(q3,b) = q4.

* The first implemented function identifies type of the given grammar according to the Chomsky hierarchy. For that purpose the set of productions is being investigated using three separate methods (the grammar is tested to be type 3, then type 2, then type 1). The conditions for the classification are a little bit simplified but are applied according to the definition. Here is the main determination function:

```
public int determineChomskyType () {
    if (checkThirdType()) {
        return 3;
    } else if (checkSecondType()) {
        return 2;
    } else if (checkFirstType()) {
        return 1;
    } else {
        return 0;
    }
}
```

* And here is an example of one of the helper methods:

```
private boolean checkFirstType () {
    List<String> productions;
    for (String state : this.P.keySet()) {
        productions = this.P.get(state);

        for (String prod : productions) {
            if ((state.length() > prod.length())  ||  prod.compareTo("e") == 0) {
                return false;
            }
        }
    }
    return true;
}
```

* The conversion of the finite automaton into a regular grammar is performed inside the newly added constructor in the `Grammar` class, which accepts as the parameter object of type `FiniteAutomaton`. The new constructor firstly calls the basic parametrized constructor and then transforms the list of transitions into the map of productions.

```
public Grammar (FiniteAutomaton fa) {
    this(fa.getQ(), fa.getSigmaAlphabet(), null, fa.getQ0());

    List<Transition> transitions = fa.getTransitions();
    Map<String, List<String>> productions = new HashMap<>();

    String st, endst;
    char param;

    for (Transition transition : transitions) {
        st = transition.getInitialState();
        endst = transition.getEndState();
        param = transition.getParameter();

        if (productions.containsKey(transition.getInitialState())) {
            productions.get(st).add(param + endst);
        } else {
            List<String> endStates = new ArrayList<>();
            endStates.add(param + endst);
            productions.put(st, endStates);
        }
    }

    this.P = productions;
}
```

* Going further, the task of identifying whether the given finite automaton is deterministic or not is not that hard. It is only necessary to check if it contains epsilon transitions or has tramsitions to different states with the same parameter starting from a single state. In this case, obviously, the automatong will be non-deterministic.

```
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
```

* Finally, the most difficult part of this laboratory work was to perform a conversion from NFA into DFA. For that purpose I used the algorithm presented at the lectures. Having the list of transitions in the default automaton we start iterating through each of its states, making tranforms, starting from the state `q0`. Each iteration represents an array containing the state which is being processed and the resulting states corresponding to the given transition parameters (terminal symbols). The states which where already worked out are added into the `visited_states` and the states which are still waiting to be processed into the `appeared states` queue. The "array" operation is being done until there are no states inside the queue. Then the arrays are added into the `states_table` which is then used to generate a new object of type `FiniteAutomaton`.

```
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
    ...
```

## Conclusions / Screenshots / Results


## References
https://www.geeksforgeeks.org/chomsky-hierarchy-in-theory-of-computation/

https://www.tutorialspoint.com/automata_theory/chomsky_classification_of_grammars.htm

https://www.javatpoint.com/automata-chomsky-hierarchy
