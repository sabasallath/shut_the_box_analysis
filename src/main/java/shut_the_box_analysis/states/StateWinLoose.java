package shut_the_box_analysis.states;

import java.util.TreeSet;

public class StateWinLoose extends State {

    StateWinLoose(TreeSet<Integer> state, int dice) {
        super(state, dice);
    }

    StateWinLoose(TreeSet<Integer> state) {
        super(state);
    }

    @Override
    int setScore() {
        if (this.isLeaf()) {
            return 1;
        } else {
            return 0;
        }
    }
}