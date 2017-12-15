package shut_the_box_analysis.dag.states;

import java.util.TreeSet;

public class StateSum extends State {

    StateSum(TreeSet<Integer> state, int dice) {
        super(state, dice);
    }

    StateSum(TreeSet<Integer> state) {
        super(state);
    }

    StateSum(State state, Integer dice) {
        super(state, dice);
    }

    @Override
    double setScore() {
        return state.stream().mapToInt(Integer::intValue).sum();    }
}
