package shut_the_box_analysis.dag.states;

import java.util.TreeSet;

public class StateConcat extends State {

    StateConcat(TreeSet<Integer> state, int dice) {
        super(state, dice);
    }

    StateConcat(TreeSet<Integer> state) {
        super(state);
    }

    @Override
    int setScore() {
        StringBuilder sb = new StringBuilder();
        state.forEach(sb::append);
        String s = sb.toString();
        return s.equals("") ? 0 : Integer.parseInt(s);
    }
}
