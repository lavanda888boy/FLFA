# Intro to formal languages. Regular grammars. Finite Automata.

### Course: Formal Languages & Finite Automata
### Author: Bajenov Sevastian, FAF-213

----

## Theory
`Formal language` is a language which consists of words whose letters are taken from an alphabet and are well-formed according to a specific set of rules 
(grammar).

`Alphabet` is a final set of symbols used in a language.

`Grammar` is a set of rules according to which the words in the language are formed defined as `G = (Vn, Vt, P, S)` where:
  1. Vn is a finite set of non-terminal symbols, typically S, A, B, ...
  2. Vt is a finite set of terminal symbols, typically 0, 1, a, b, ...
  3. P is a finite set of productions
  4. S is the starting symbol
  
`Finite automaton` or `Finite state machine` is defined by the following form `M = (Q, Sigms, delta, q0, F)` where:
  1. Q is the finite set of states
  2. Sigma is an alphabet
  3. delta is the finite set of transitions or the transition function
  4. q0 is the initial state
  5. F is the finite set of final states


## Objectives:

1. Understand what a language is and what it needs to have in order to be considered a formal one.

2. Provide the initial setup for the evolving project that you will work on during this semester.

    a. Create a local && remote repository of Github;

    b. Choose a programming language, that supports all the main paradigms;

    c. Create a separate folder where you will be keeping the report;

3. According to your variant number, get the grammar definition and do the following tasks:

    a. Implement a type/class for your grammar;

    b. Add one function that would generate 5 valid strings from the language expressed by your given grammar;

    c. Implement some functionality that would convert and object of type Grammar to one of type Finite Automaton;

    d. For the Finite Automaton, add a method that checks if an input string can be obtained via the state transition from it;


## Implementation description

* The following code corresponds to the variant number 2

  VN={S, R, L}, 
  
  VT={a, b, c, d, e, f},
  
  P={ 
    S → aS
    S → bS
    S → cR
    S → dL
    R → dL
    R → e
    L → fL
    L → eL
    L → d
  }

* The `Grammar` class is implemented according to the definition of a language's grammar. It is being caracterized by the four main parameters:
set of terminal characters `(V_n)`, set of non-terminal characters `(V_t)`, set of productions `(P)` and the start symbol `(S)`. The first two parameters
are presented as two String attributes of the class; a map represents the productions set (mapping each non-terminal character to the possible resulting
string); and the start symbol is simply a char attribute.
```
public class Grammar {

    private String V_n;
    private String V_t;
    private Map<Character, List<String>> P;
    private char S;

    public Grammar (String V_n, String V_t, Map<Character, List<String>> P, char S) {
        this.V_n = V_n;
        this.V_t = V_t;
        this.P = P;
        this.S = S;
    }
    ...
```

* The following function generates a random valid string from the given grammar. Infinite while-loop terminates only when there are no more non-terminal 
characters inside the produced string. If they are still present, then one of them is picked up, its possible productions are extracted from the map and
then one production is chosen randomly. The corresponding non-terminal symbol is substituted by the picked up production.

```
public String generateString () {
        StringBuffer word = new StringBuffer();
        word.append(S);

        Random r = new Random();

        while (!isWord(word.toString())) {
            int nonterm = findNonTerminalChar(word);
            int production = r.nextInt(0, P.get(word.charAt(nonterm)).size());
            word.replace(nonterm, nonterm + 1, P.get(word.charAt(nonterm)).get(production));
        }

        return word.toString();
    }
```
This method also requires two additional methods: one for checking if the string represents a word (contains only terminal symbols) and another one for finding the remaining non-ternminal character in the modified string.
```
private boolean isWord (String arbitrary_word) {
        String copy = arbitrary_word.toLowerCase();

        if (copy.compareTo(arbitrary_word) == 0) {
            return true;
        } else return false;
    }

    private int findNonTerminalChar (StringBuffer sb) {
        for (int i = 0; i < sb.length(); i++) {
            if (Character.isUpperCase(sb.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
```

* Function `toFiniteAutomaton()` makes conversion from type `Grammar` to `FinalAutomaton` by applying a set of very concrete rules. The set of states `(Q)`
is obtained by taking the set of all non-terminal characters and adding an 'X' to them. Alphabet is nothing else but the set of all terminal symbols. Start
symbol also remains the same. The list of all transitions is formed by iterating through the map of productions and splitting each element into states and
transition parameters. And the final states are collected into a set by extrcting the states from the productions map for which there is only one possible
production which leads to a single terminal symbol.
```
public FiniteAutomaton toFiniteAutomaton() {
        List<Transition> transitions = new ArrayList<>();

        for (Character state : this.P.keySet()) {
            for (String str : P.get(state)) {
                Transition t;

                if (str.length() == 1) {
                    t = new Transition(state, 'X', str.charAt(0));
                } else {
                    t = new Transition(state, str.charAt(1), str.charAt(0));
                }
                
                transitions.add(t);
            }
        }

        Set<Character> finalStates = new HashSet<>();
        finalStates.add('X');
        for (Character state : this.P.keySet()) {
            if (this.P.get(state).size() == 1  &&  this.P.get(state).get(0).length() == 1) {
                finalStates.add(state);
            }
        }

        return new FiniteAutomaton(V_n + "X", V_t, transitions, this.S, finalStates);
    }
```
And the `FiniteAutomaton` class itself, implemented according to its definition.
```
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
    ...
```
With a separate class representing each `Transition`.
```
public class Transition {
    
    private char initialState;
    private char endState;
    private char parameter;

    public Transition (char initialState, char endState, char parameter) {
        this.initialState = initialState;
        this.endState = endState;
        this.parameter = parameter;
    }
```

* The final implemented method in this laboratory work checks if the arbitrary string can be obtained using the given final automaton. Basically, it iterates through the input string, taking its characters one by one. At the same time the program keeps track of the current non-terminal symbol by applying transitions to it (starting from 'S'). If the conditions are respected and the final transition leads to a terminal state then the string is valid, otherwise not.
```
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
```
The above function also requires a method which cheks if the identified transition inside the string might exist.
```
private char existsTransition (char state, char parameter) {
        for (Transition t : delta) {
            if (t.getInitialState() == state  &&  t.getParameter() == parameter) {
                return t.getEndState();
            }
        }
        return '-';
    }
```

## Conclusions / Screenshots / Results
The code of the laboratory work was written in Java programming language and here is the example of output of the `Main` class. The generated string, the attributes of the finite automaton and the result of the isWord() method applied to the generated string can be seen in the terminal window.

<p align=center><img src=https://github.com/lavanda888boy/FLFA_labs/blob/ff64b3a324a54e7784b69c64e106584be3d44234/screenshots/Lab_1/results.png /></p>

To sum up the entire laboratory work, I've studied many important concepts by implementing them using programming languages. I also applied my knowledge of 
data structures to optimixe the implementation. For instance, I used map data structure to define the set of productions for the given grammar. Moreover, I
noticed that the finite automaton itself can be presented as a directed weighted graph (where the weights will be actually terminal symbols). This knowledge gives me the opportuntity how to design a lexer and optimize it for identifying the tokens of a language (this concept will be especially useful in my current PBL project on designing a formal language).


## References
https://redirect.cs.umbc.edu/portal/help/theory/lang_def.shtml

https://redirect.cs.umbc.edu/portal/help/theory/automata_def.shtml

https://en.wikipedia.org/wiki/Formal_language
