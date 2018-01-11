package shut_the_box_analysis.states;

import shut_the_box_analysis.box.BoxConst;

import java.util.TreeSet;

public class StateReachOne extends State {

    StateReachOne(TreeSet<Integer> state, int dice) {
        super(state, dice, -1);
    }

    StateReachOne(TreeSet<Integer> state) {
        super(state, -1);
    }

    @Override
    int setScore() {
        if (this.getState().stream().mapToInt(Integer::intValue).sum() == BoxConst.MIN.get()) {
            return 1;
        } else {
            return 0;
        }
    }
}