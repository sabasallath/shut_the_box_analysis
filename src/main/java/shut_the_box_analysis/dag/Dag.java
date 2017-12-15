package shut_the_box_analysis.dag;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import shut_the_box_analysis.Decomposition;
import shut_the_box_analysis.box.Box;
import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.dag.states.StateFactory;
import shut_the_box_analysis.dice.Dice;
import shut_the_box_analysis.dice.DiceProbability;
import shut_the_box_analysis.dag.states.State;

import java.util.*;
import java.util.function.Consumer;

public class Dag {

    private final Decomposition decomposition;
    private final HashMap<Integer, State> transitionTreeMutable;
    private final State root;
    private final State leaf;
    private final StateFactory factory;

    public Dag(CostType costType) {
        this.factory = new StateFactory(costType);
        root = factory.state(Sets.newTreeSet(Box.irange()));
        leaf = factory.state(Sets.newTreeSet());
        this.transitionTreeMutable = Maps.newHashMap();
        this.decomposition = new Decomposition(factory);
        createTransitionDag();
        assignCost();
    }

    /* -------------------------------------------------------
        Create transition Tree
     -------------------------------------------------------*/

    private void createTransitionDag() {
        transitionTreeMutable.put(root.hashCode(), root);
        transitionTreeMutable.put(leaf.hashCode(), leaf);
        addChanceNode(root);
    }

    private void addChanceNode(State current) {
        for (Integer i : Dice.irange()) {
            State nextState = factory.state(current, i);
            link(current, nextState, this::addDecisionNode);
        }
    }

    private void addDecisionNode(State current) {
        for (State nextState : decomposition.getValid(current)) {
            link(current, nextState, this::addChanceNode);
        }
    }

    private void link(State current, State nextState, Consumer<State> f) {
        if (transitionTreeMutable.containsKey(nextState.hashCode())) {
            nextState = transitionTreeMutable.get(nextState.hashCode());
        } else {
            transitionTreeMutable.put(nextState.hashCode(), nextState);
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

    public ImmutableMap<Integer, State> getMap() {
        return ImmutableMap.copyOf(transitionTreeMutable);
    }

    public State getRoot() {
        return root;
    }

    public State getLeaf() {
        return leaf;
    }

    public State get(int hashcode) {
        return transitionTreeMutable.get(hashcode);
    }

    public StateFactory getFactory() {
        return factory;
    }
}
