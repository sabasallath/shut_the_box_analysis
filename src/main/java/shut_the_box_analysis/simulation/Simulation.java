package shut_the_box_analysis.simulation;

import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dice.Dice;
import shut_the_box_analysis.states.State;

import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Simulation {

    private final Dag dag;

    public Simulation(Dag dag) {
        this.dag = dag;
    }

    public State run(boolean print) {
        StringJoiner sb = new StringJoiner("\n", "\n", "\n");
        if (print) sb.add(dag.getRoot().stateAndCost());

        State current = dag.getRoot();
        State previous = current;
        while (current != null) {
        	previous = current;
            if (print) simulatePrinter(sb, current);

            if (current.dice() == 0) {
                if (current == dag.getLeaf()) break;
                int roll = Dice.roll();
                if (print) sb.add("Dice = " + roll);
                current = dag.get(dag.getFactory().state(current.getState(), roll).hashCode());
            } else {
                current = current.getStrategyNext();
            }
        }

        if (print) System.out.println(sb.toString());

        return previous;
    }

    private void simulatePrinter(StringJoiner sb, State current) {
        sb.add("------------------")
                .add("Current :" + current.stateAndCost())
                .add("Next List :")
                .add(current.getNext().stream()
                        .map(State::stateAndCost)
                        .collect(Collectors.joining("\n")));
    }

    public State run() {
        return run(false);
    }
}
