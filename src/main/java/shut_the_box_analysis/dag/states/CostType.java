package shut_the_box_analysis.dag.states;

public enum CostType {
    SUM("sum"),
    CONCAT("concat"),
    WIN_LOOSE("win_loose"),
    REACH_ONE("reach_one");

    private final String s;

    CostType(String s) {
        this.s = s;
    }

    @Override
    public String toString(){
        return s;
    }
}
