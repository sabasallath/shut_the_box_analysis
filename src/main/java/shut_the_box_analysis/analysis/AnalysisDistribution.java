package shut_the_box_analysis.analysis;

import java.util.HashMap;
import com.google.common.collect.Maps;
import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.dag.states.State;
import shut_the_box_analysis.dice.DiceProbability;
import shut_the_box_analysis.io.Csv;

public class AnalysisDistribution {

    private final Dag dag;
    private final CostType costType;
    private final StrategyType strategy;
    private HashMap<Integer, Double> statesByCost;
    private HashMap<State, Integer> visitedCounter;
    private HashMap<State, Double> statesProbatility;
    private double totalProb;

    public AnalysisDistribution(Dag dag) {
        this.dag = dag;
        this.costType = dag.getFactory().getType();
        this.strategy = dag.getStrategy();
        this.statesByCost = Maps.newHashMap();
        this.statesProbatility = Maps.newHashMap();
        this.visitedCounter = Maps.newHashMap();
        this.totalProb = 0.0;

        State root = dag.getRoot();
        markVisitRoot(root);
        statesProbatility.put(root, 1.0);
        exploreChanceNode(root);
        distribution();
        display();
        write();
    }

    public double getTotalProb() {
        return totalProb;
    }

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
        return visitedCounter.get(current) == current.getPrevious().size();
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

    private void putCost(Integer cost, Double prob) {
        if (! statesByCost.containsKey(cost)) {
            statesByCost.put(cost, prob);
        } else {
            statesByCost.replace(cost, statesByCost.get(cost) + prob);
        }
    }

    private void distribution() {
        totalProb = 0;
        for (State state : statesProbatility.keySet()) {
            if (state.hasNext()) {
                Double prob = statesProbatility.get(state);
                totalProb += prob;
                putCost(state.getScore(), prob);
            }
        }
    }

    public void write() {
        Csv csv = new Csv(getClass().getName(), "distribution", costType, strategy);
        csv.add("Sum", "Probability");
        for (Integer i : statesByCost.keySet()) {
            double prob = statesByCost.get(i);
            csv.add(i, prob);
        }
        csv.write();
    }

    private void display() {
        for (Integer i : statesByCost.keySet()) {
            double prob = statesByCost.get(i);
            System.out.println("Cost " +  i + ", prob = " + prob);
        }
        System.out.println("Total sum = " + totalProb);
    }

}
