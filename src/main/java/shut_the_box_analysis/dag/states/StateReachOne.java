package shut_the_box_analysis.dag.states;

import shut_the_box_analysis.box.BoxConst;
import shut_the_box_analysis.dice.DiceConst;

import java.util.TreeSet;

public class StateReachOne extends State {

    StateReachOne(TreeSet<Integer> state, int dice) {
        super(state, dice);
    }

    StateReachOne(TreeSet<Integer> state) {
        super(state);
    }

    StateReachOne(State chanceState, Integer dice) {
        super(chanceState, dice);
    }

    @Override
    double setScore() {
        if (this.getState().stream().mapToInt(Integer::intValue).sum() == BoxConst.MIN.get()) {
            return 1.0;
        } else {
            return 0;
        }
    }
}