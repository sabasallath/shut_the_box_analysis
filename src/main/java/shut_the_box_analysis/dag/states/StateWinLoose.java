package shut_the_box_analysis.dag.states;

import java.util.TreeSet;

public class StateWinLoose extends State {

    StateWinLoose(TreeSet<Integer> state, int dice) {
        super(state, dice);
    }

    StateWinLoose(TreeSet<Integer> state) {
        super(state);
    }

    StateWinLoose(State chanceState, Integer dice) {
        super(chanceState, dice);
    }

    @Override
    double setScore() {
        if (this.isLeaf()) {
            return 1;
        } else {
            return 0;
        }
    }
}