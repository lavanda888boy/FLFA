package analysis.parser;

public class Expression {
    
    private String firstOperand;
    private String operator;
    private String secondOperand;

    public Expression (String value) {
        this.firstOperand = value;
    }

    public Expression (String firstOperand, String operator, String secondOperand) {
        this.firstOperand = firstOperand;
        this.operator = operator;
        this.secondOperand = secondOperand;
    }

    public String getFirstOperand () {
        return this.firstOperand;
    }

    public String getOperator () {
        return this.operator;
    }

    public String getSecondOperand () {
        return this.secondOperand;
    }
}
