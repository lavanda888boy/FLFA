package analysis.parser;

import java.util.ArrayList;
import java.util.List;

public class Statement {
    
    private String variableName;
    private List<Expression> value;

    public Statement (String variableName) {
        this.variableName = variableName;
        value = new ArrayList<>();
    }

    public void setValue (List<Expression> value) {
        this.value = value;
    }
}
