package shut_the_box_analysis.states;

import java.util.TreeSet;

public class StateSum extends State {

    StateSum(TreeSet<Integer> state, int dice) {
        super(state, dice, -1);
    }

    StateSum(TreeSet<Integer> state) {
        super(state, -1);
    }

    @Override
    int setScore() {
        return state.stream().mapToInt(Integer::intValue).sum();    }
}
