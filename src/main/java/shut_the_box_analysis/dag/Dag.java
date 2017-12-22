package shut_the_box_analysis.dag;

import com.google.common.collect.*;
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
    private final HashMap<State, State> leafs;
    private HashMap<State, Integer> visitedCounter;
    private StrategyType strategy;

    public Dag(CostType costType, StrategyType strategy) {
        this.factory = new StateFactory(costType);
        this.strategy = strategy;
        this.root = factory.state(Sets.newTreeSet(Box.irange()));
        if (costType == CostType.REACH_ONE) {
            this.leaf = factory.state(Sets.newTreeSet(Lists.newArrayList(1)));
        } else {
            this.leaf = factory.state(Sets.newTreeSet());
        }
        this.leafs = Maps.newHashMap();
        this.transitionTreeMutable = Maps.newHashMap();
        this.decomposition = new Decomposition(factory);
        createTransitionDag();
//        assignCost();
        this.visitedCounter = Maps.newHashMap();
        leafs.put(leaf, leaf);
        assignTrueCost(leafs.keySet());
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
        ImmutableList<State> next = decomposition.getValid(current);
        if (next.isEmpty()) {
            leafs.putIfAbsent(current, current);
        }
        for (State nextState : next) {
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

//    private void assignCost() {
//        ArrayDeque<State> todoState = new ArrayDeque<>();
//        todoState.addAll(leaf.getPrevious());
//
//        while (!todoState.isEmpty()) {
//            State current = todoState.pollFirst();
//            addNotDonePrevious(todoState, current);
//            if (current.dice() == 0) {
//                double sum = current.getNext().stream()
//                        .mapToDouble(e -> DiceProbability.get(e.dice()) * e.getCost())
//                        .sum();
//                current.setCost(sum);
//            } else {
//                State next = strategy.get().apply(current);
//                current.setCost(next.getCost());
//                current.setStrategyNext(next);
//            }
//        }
//    }
//
//    private void addNotDonePrevious(ArrayDeque<State> todo, State current) {
//        for (State previous : current.getPrevious()) {
//            if (!todo.contains(previous)) {
//                todo.add(previous);
//            }
//        }
//    }

    private void markVisitRoot(State root) {
        visitedCounter.put(root, -1);
    }

    private void markVisit(State current) {
        if (! visitedCounter.containsKey(current)) {
            visitedCounter.put(current, 1);
        } else {
            visitedCounter.replace(current, visitedCounter.get(current) + 1);
        }
    }

    private boolean visited(State current) {
        return visitedCounter.get(current) == current.getNext().size();
    }

    private void explore(State current) {
        markVisit(current);
        if (visited(current)) {
            if(! current.getNext().isEmpty()) {
                if (current.dice() == 0) {
                    double sum = current.getNext().stream()
                            .mapToDouble(e -> DiceProbability.get(e.dice()) * e.getCost())
                            .sum();
                    current.setCost(sum);
                } else {
                    Optional<State> optionalNext = strategy.get().apply(current);
                    if (optionalNext.isPresent()) {
                        State next = optionalNext.get();
                        current.setCost(next.getCost());
                        current.setStrategyNext(next);
                    }
                }
            }

            for (State previous : current.getPrevious()) {
                explore(previous);
            }
        }
    }

    private void assignTrueCost(Set<State> states) {
        for (State state : states) {
            markVisitRoot(state);
            explore(state);
        }
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

    public StrategyType getStrategy() {
        return strategy;
    }
}
