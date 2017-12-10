package shut_the_box_analysis;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import shut_the_box_analysis.box.Box;
import shut_the_box_analysis.dice.Dice;
import shut_the_box_analysis.dice.DiceProbability;
import shut_the_box_analysis.states.State;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ShutTheBox {

    private final Decomposition decomposition;
    private final State root;
    private final State leaf;
    private HashMap<Integer, State> transitionTree;

    public ShutTheBox() {

        this.transitionTree = Maps.newHashMap();
        this.decomposition = new Decomposition();

        root = new State(Sets.newTreeSet(Box.irange()));
        leaf = new State(Sets.newTreeSet());
        createTransitionTree();
        assignCost();
//        simulate();
    }

    /* -------------------------------------------------------
        Create transition Tree
     -------------------------------------------------------*/

    private void createTransitionTree() {
        transitionTree.put(root.hashCode(), root);
        transitionTree.put(leaf.hashCode(), leaf);
        addChanceNode(root);
    }

    private void addChanceNode(State current) {
        for (Integer i : Dice.irange()) {
            State nextState = new State(current, i);
            link(current, nextState, this::addDecisionNode);
        }
    }

    private void addDecisionNode(State current) {
        for (State nextState : decomposition.getValid(current)) {
            link(current, nextState, this::addChanceNode);
        }
    }

    private void link(State current, State nextState, Consumer<State> f) {
        if (transitionTree.containsKey(nextState.hashCode())) {
            nextState = transitionTree.get(nextState.hashCode());
        } else {
            transitionTree.put(nextState.hashCode(), nextState);
            f.accept(nextState);
        }

        current.addNext(nextState);
        nextState.addPrevious(current);
    }

    /* -------------------------------------------------------
        Assign Cost
     -------------------------------------------------------*/

    private void assignCost() {
        ArrayDeque<State> todoState = new ArrayDeque<>();
        todoState.add(leaf);

        while (!todoState.isEmpty()) {
            State current = todoState.pollFirst();
            addNotDonePrevious(todoState, current);
            if (current.dice() == 0) {
                double sum = current.getNext().stream()
                        .mapToDouble(e -> DiceProbability.get(e.dice()) * e.getCost())
                        .sum();
                current.setCost(sum);
            } else {
                State min = current.getNext().stream()
                        .min(Comparator.comparingDouble(State::getCost))
                        .orElseThrow(() -> new RuntimeException("No next - should never happen."));
                current.setCost(min.getCost());
                current.setMinNext(min);
            }
        }
    }

    private void addNotDonePrevious(ArrayDeque<State> todo, State current) {
        for (State previous : current.getPrevious()) {
            if (!todo.contains(previous)) {
                todo.add(previous);
            }
        }
    }


    /* -------------------------------------------------------
        Simulate
     -------------------------------------------------------*/

    public boolean simulate(boolean print) {
        StringJoiner sb = new StringJoiner("\n", "\n", "\n");
        if (print) sb.add(root.stateAndCost());

        State current = transitionTree.get(root.hashCode());

        while (current != null) {

            if (print) simulatePrinter(sb, current);

            if (current.dice() == 0) {
                if (current == leaf) break;
                int roll = Dice.roll();
                if (print) sb.add("Dice = " + roll);
                current = transitionTree.get(new State(current.getState(), roll).hashCode());
            } else {
                current = current.getMinNext();
            }
        }

        if (print) System.out.println(sb.toString());

        return current == leaf;
    }

    private void simulatePrinter(StringJoiner sb, State current) {
        sb.add("------------------")
                .add("Current :" + current.stateAndCost())
                .add("Next List :")
                .add(current.getNext().stream()
                .map(State::stateAndCost)
                .collect(Collectors.joining("\n")));
    }

    public HashMap<Integer, State> getTransitionTree() {
        return transitionTree;
    }
}
