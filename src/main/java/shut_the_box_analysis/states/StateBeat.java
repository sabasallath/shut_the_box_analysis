package shut_the_box_analysis.states;

import java.util.TreeSet;

public class StateBeat extends State {

    StateBeat(TreeSet<Integer> state, int dice, int valueToBeat) {
        super(state, dice, valueToBeat);
    }

    StateBeat(TreeSet<Integer> state, int valueToBeat) {
        super(state, valueToBeat);
    }

    @Override
    int setScore() {
        if (this.getState().stream().mapToInt(Integer::intValue).sum() <= getValueToBeat()) {
            return 1;
        } else {
            return 0;
        }
    }
}
