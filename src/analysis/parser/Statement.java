package analysis.parser;

import java.util.ArrayList;
import java.util.List;

public class Statement {
    
    private String variableName;
    private List<Expression> value;
    private List<String> expressionOperators;

    public Statement (String variableName) {
        this.variableName = variableName;
        value = new ArrayList<>();
        expressionOperators = new ArrayList<>();
    }

    public String getVariableName () {
        return this.variableName;
    }

    public List<Expression> getValue () {
        return this.value;
    }

    public List<String> getExpressionOperators () {
        return this.expressionOperators;
    }

    public void setValue (List<Expression> value) {
        this.value = value;
    }

    public void setExpressionOperators (List<String> expressionOperators) {
        this.expressionOperators = expressionOperators;
    }
}
