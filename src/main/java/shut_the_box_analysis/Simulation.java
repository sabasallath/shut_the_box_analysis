package shut_the_box_analysis;

import shut_the_box_analysis.TransitionDag.TransitionDag;
import shut_the_box_analysis.dice.Dice;
import shut_the_box_analysis.states.State;

import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Simulation {

    public boolean run(boolean print) {
        StringJoiner sb = new StringJoiner("\n", "\n", "\n");
        if (print) sb.add(TransitionDag.getRoot().stateAndCost());

        State current = TransitionDag.getRoot();

        while (current != null) {

            if (print) simulatePrinter(sb, current);

            if (current.dice() == 0) {
                if (current == TransitionDag.getLeaf()) break;
                int roll = Dice.roll();
                if (print) sb.add("Dice = " + roll);
                current = TransitionDag.get(new State(current.getState(), roll).hashCode());
            } else {
                current = current.getMinNext();
            }
        }

        if (print) System.out.println(sb.toString());

        return current == TransitionDag.getLeaf();
    }

    private void simulatePrinter(StringJoiner sb, State current) {
        sb.add("------------------")
                .add("Current :" + current.stateAndCost())
                .add("Next List :")
                .add(current.getNext().stream()
                        .map(State::stateAndCost)
                        .collect(Collectors.joining("\n")));
    }
}
