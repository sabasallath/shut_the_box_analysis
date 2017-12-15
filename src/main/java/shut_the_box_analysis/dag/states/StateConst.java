package shut_the_box_analysis.dag.states;

public enum StateConst {
    CHANCE_STATE_SIZE(9),
    CHANCE_LAST((int) Math.pow(2, CHANCE_STATE_SIZE.get()) - 1);

    private final int v;

    StateConst(int size) {
        this.v = size;
    }

    public int get() {
        return v;
    }

    @Override
    public String toString(){
        return Integer.toString(v);
    }
}
