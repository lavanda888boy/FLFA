package automaton;

public class Transition {
    
    private String initialState;
    private String endState;
    private char parameter;

    public Transition (String initialState, String endState, char parameter) {
        this.initialState = initialState;
        this.endState = endState;
        this.parameter = parameter;
    }

    public String getInitialState () {
        return this.initialState;
    }    
    
    public void setInitialState (String initialState) {
        this.initialState = initialState;
    }

    public String getEndState () {
        return this.endState;
    }    
    
    public void setEndState (String endState) {
        this.endState = endState;
    }

    public char getParameter () {
        return this.parameter;
    }    
    
    public void setParameter (char parameter) {
        this.parameter = parameter;
    }
}
