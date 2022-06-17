package SAT;

public class Literal {


    private int variable;
    private int value;


    public Literal(int variable, int value){
        this.value = value;
        this.variable = variable;
    }
    public int getValue() {
        return value;
    }

    public int getVariable() {
        return variable;
    }

}
