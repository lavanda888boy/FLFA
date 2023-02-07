package automaton;

public class Transition {
    
    private char initialState;
    private char endState;
    private char parameter;

    public Transition (char initialState, char endState, char parameter) {
        this.initialState = initialState;
        this.endState = endState;
        this.parameter = parameter;
    }

    public char getInitialState () {
        return this.initialState;
    }    
    
    public void setInitialState (char initialState) {
        this.initialState = initialState;
    }

    public char getEndState () {
        return this.endState;
    }    
    
    public void setEndState (char endState) {
        this.endState = endState;
    }

    public char getParameter () {
        return this.parameter;
    }    
    
    public void setParameter (char parameter) {
        this.parameter = parameter;
    }
}
