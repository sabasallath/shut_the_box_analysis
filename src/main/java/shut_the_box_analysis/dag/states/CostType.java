package shut_the_box_analysis.dag.states;

public enum CostType {
    SUM("sum"),
    CONCAT("concat");

    private final String s;

    CostType(String s) {
        this.s = s;
    }

    @Override
    public String toString(){
        return s;
    }
}
