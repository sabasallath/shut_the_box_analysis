package shut_the_box_analysis.dag;

import com.google.common.collect.*;
import shut_the_box_analysis.box.Box;
import shut_the_box_analysis.states.CostType;
import shut_the_box_analysis.states.StateFactory;
import shut_the_box_analysis.dice.Dice;
import shut_the_box_analysis.dice.DiceProbability;
import shut_the_box_analysis.states.State;

import java.util.*;
import java.util.function.Consumer;

public class Dag {

    private final Decomposition decomposition;
    private final HashMap<Integer, State> transitionDag;
    private final HashMap<State, State> leafs;
    private final HashMap<State, Integer> visitedCounter;
    private final StateFactory factory;
    private final StrategyType strategy;
    private final State root;
    private final State leaf;

    public Dag(CostType costType, StrategyType strategy) {
        this.factory = new StateFactory(costType);
        this.strategy = strategy;
        this.root = factory.state(Sets.newTreeSet(Box.irange()));
        this.leaf = chooseLeaf(costType);
        this.decomposition = new Decomposition(factory);
        this.leafs = Maps.newHashMap();
        this.transitionDag = Maps.newHashMap();
        this.visitedCounter = Maps.newHashMap();
        createTransitionDag();
        assignCost();
    }

    private State chooseLeaf(CostType costType) {
        if (costType == CostType.REACH_ONE) {
            return factory.state(Sets.newTreeSet(Lists.newArrayList(1)));
        } else {
            return factory.state(Sets.newTreeSet());
        }
    }

    /* -------------------------------------------------------
        Create transition Tree
     -------------------------------------------------------*/

    private void createTransitionDag() {
        transitionDag.put(root.hashCode(), root);
        transitionDag.put(leaf.hashCode(), leaf);
        addChanceNode(root);
    }

    private void addChanceNode(State current) {
        for (Integer i : Dice.irange()) {
            State nextState = factory.state(current, i);
            link(current, nextState, this::addDecisionNode);
        }
    }

    private void addDecisionNode(State current) {
        ImmutableList<State> next = decomposition.getValid(current);
        if (next.isEmpty()) {
            leafs.putIfAbsent(current, current);
        }
        for (State nextState : next) {
            link(current, nextState, this::addChanceNode);
        }
    }

    private void link(State current, State nextState, Consumer<State> f) {
        if (transitionDag.containsKey(nextState.hashCode())) {
            nextState = transitionDag.get(nextState.hashCode());
        } else {
            transitionDag.put(nextState.hashCode(), nextState);
            f.accept(nextState);
        }

        current.addNext(nextState);
        nextState.addPrevious(current);
    }

    /* -------------------------------------------------------
        Assign Cost
     -------------------------------------------------------*/

    private void markVisitRoot(State root) {
        visitedCounter.put(root, -1);
    }

    private void incrementVisit(State current) {
        visitedCounter.merge(current, 1, (v1, v2) -> v1 + v2);
    }

    private boolean visited(State current) {
        return visitedCounter.get(current) == current.getNext().size();
    }

    private void explore(State current) {
        incrementVisit(current);
        if (visited(current)) {
            if(! current.hasNext()) {
                if (current.dice() == 0) {
                    double sum = current.getNext().stream()
                            .mapToDouble(e -> DiceProbability.get(e.dice()) * e.getCost())
                            .sum();
                    current.setCost(sum);
                } else {
                    State next = strategy.get().apply(current);
                    current.setCost(next.getCost());
                    current.setStrategyNext(next);
                }
            }

            for (State previous : current.getPrevious()) {
                explore(previous);
            }
        }
    }

    private void assignCost() {
        leafs.put(leaf, leaf);
        for (State state : leafs.keySet()) {
            markVisitRoot(state);
            explore(state);
        }
    }

    /* -------------------------------------------------------
        Getter
     -------------------------------------------------------*/

    public State getRoot() {
        return root;
    }

    public State getLeaf() {
        return leaf;
    }

    public State get(int hashcode) {
        return transitionDag.get(hashcode);
    }

    public StateFactory getFactory() {
        return factory;
    }

    public StrategyType getStrategy() {
        return strategy;
    }
}
