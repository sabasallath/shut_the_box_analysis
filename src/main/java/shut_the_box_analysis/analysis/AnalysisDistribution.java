package shut_the_box_analysis.analysis;

import java.util.HashMap;
import com.google.common.collect.Maps;
import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.dag.states.State;
import shut_the_box_analysis.dice.DiceProbability;

public class AnalysisDistribution {

    private final Dag dag;
    private final CostType costType;
    private final StrategyType strategy;
    private HashMap<Integer, Double> statesByCost;
    private HashMap<State, Integer> visitedCounter;
    private HashMap<State, Double> statesProbatility;
    private double totalSum;

    public AnalysisDistribution() {
        this.dag = new Dag(CostType.SUM, StrategyType.RANDOM);
        this.costType = dag.getFactory().getType();
        this.strategy = dag.getStrategy();
        this.statesByCost = Maps.newHashMap();
        this.statesProbatility = Maps.newHashMap();
        this.visitedCounter = Maps.newHashMap();
        this.totalSum = 0.0;

        State root = dag.getRoot();
        statesProbatility.put(root, 1.0);
        markVisit(root);
        for (State state : root.getNext()) {
            statesProbatility.put(state, DiceProbability.get(state.dice()));
            exploreDecisionNode(state);
        }
        distribution();
        display();
    }

    private void display() {
        for (Integer i : statesByCost.keySet()) {
            Double prob = statesByCost.get(i);
            System.out.println("Cost " +  i + ", prob = " + prob);
        }
        System.out.println("Total sum = " + totalSum);
    }

    private boolean visited(State current) {
        return visitedCounter.get(current) == current.getPrevious().size();
    }

    private void exploreChanceNode(State current) {
        markVisit(current);
        if (visited(current)) {
            for (State next : current.getNext()) {
                putProb(current, next, DiceProbability.get(next.dice()));
                exploreDecisionNode(next);
            }
        }
    }

    private void exploreDecisionNode(State current) {
        markVisit(current);
        if (visited(current)) {
            State sn = current.getStrategyNext();
            for (State next : current.getNext()) {
                if (next == sn) {
                    putProb(current, next, 1.0);
                } else {
                    putProb(current, next, 0.0);
                }
                exploreChanceNode(next);
            }
        }
    }

    private void putProb(State current, State next, Double factor) {
        Double prob = statesProbatility.get(current) * factor;
        if (! statesProbatility.containsKey(next)) {
            statesProbatility.put(next, prob);
        } else {
            Double currentCost = statesProbatility.get(next);
            statesProbatility.replace(next, currentCost + prob);
        }
    }

    private void markVisit(State current) {
        if (! visitedCounter.containsKey(current)) {
            visitedCounter.put(current, 1);
        } else {
            visitedCounter.replace(current, visitedCounter.get(current) + 1);
        }
    }

    private void putCost(Integer cost, Double prob) {
        if (! statesByCost.containsKey(cost)) {
            statesByCost.put(cost, prob);
        } else {
            statesByCost.replace(cost, statesByCost.get(cost) + prob);
        }
    }

    private void distribution() {
        totalSum = 0;
        for (State state : statesProbatility.keySet()) {
            if (state.hasNext()) {
                Double prob = statesProbatility.get(state);
                totalSum += prob;
                putCost(state.getScore(), prob);
            }
        }
    }
}
