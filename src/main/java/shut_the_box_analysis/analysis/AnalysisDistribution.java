package shut_the_box_analysis.analysis;

import java.util.HashMap;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.states.CostType;
import shut_the_box_analysis.states.State;
import shut_the_box_analysis.dice.DiceProbability;
import shut_the_box_analysis.io.Csv;

public class AnalysisDistribution {

    private final static Logger logger = LoggerFactory.getLogger(AnalysisDistribution.class);

    private final CostType costType;
    private final StrategyType strategy;
    private HashMap<Integer, Double> statesByCost;
    private HashMap<State, Integer> visitedCounter;
    private HashMap<State, Double> statesProbatility;
    private double totalProb;
    private boolean write;

    public AnalysisDistribution(Dag dag, boolean write) {
        this.costType = dag.getFactory().getType();
        this.strategy = dag.getStrategy();
        this.write = write;
        this.statesByCost = Maps.newHashMap();
        this.statesProbatility = Maps.newHashMap();
        this.visitedCounter = Maps.newHashMap();
        this.totalProb = 0.0;

        State root = dag.getRoot();
        markVisitRoot(root);
        statesProbatility.put(root, 1.0);
        exploreChanceNode(root);
        distribution();
        write();
        if (costType == CostType.CONCAT) {
            distributionSumConcat();
        }
    }

    public AnalysisDistribution(Dag dag) {
        this(dag, true);
    }

    private void markVisitRoot(State root) {
        visitedCounter.put(root, -1);
    }

    double getTotalProb() {
        return totalProb;
    }

    private void incrementVisit(State current) {
        visitedCounter.merge(current, 1, (v1, v2) -> v1 + v2);
    }

    private boolean visited(State current) {
        return visitedCounter.get(current) == current.getPrevious().size();
    }

    private void updateProbability(State current, State next, Double factor) {
        Double prob = statesProbatility.get(current) * factor;
        statesProbatility.merge(next, prob, (v1, v2) -> v1 + v2);
    }

    private void exploreChanceNode(State current) {
        incrementVisit(current);
        if (visited(current)) {
            for (State next : current.getNext()) {
                updateProbability(current, next, DiceProbability.get(next.dice()));
                exploreDecisionNode(next);
            }
        }
    }

    private void exploreDecisionNode(State current) {
        incrementVisit(current);
        if (visited(current)) {
            State sn = current.getStrategyNext();
            for (State next : current.getNext()) {
                if (next == sn) {
                    updateProbability(current, next, 1.0);
                } else {
                    updateProbability(current, next, 0.0);
                }
                exploreChanceNode(next);
            }
        }
    }

    private void updateCost(Integer cost, Double prob) {
        statesByCost.merge(cost, prob, (v1, v2) -> v1 + v2);
    }

    private void distribution() {
        if (costType == CostType.BEAT) {
            logger.info("Analyse twoPlayer part : " + costType.toString() + ", " + strategy.toString());
        } else {
            logger.info("Analyse distribution   : " + costType.toString() + ", " + strategy.toString());
        }

        totalProb = 0;
        for (State state : statesProbatility.keySet()) {
            if (state.hasNext()) {
                Double prob = statesProbatility.get(state);
                totalProb += prob;
                updateCost(state.getScore(), prob);
            }
        }
    }

    public void write() {
        write(statesByCost, "distribution");
    }

    /**
     * To compare the use of the strategy concat with the sum rule.
     */
    private void distributionSumConcat() {
        HashMap<Integer, Double> bySum = Maps.newHashMap();
        for (State state : statesProbatility.keySet()) {
            if (state.hasNext()) {
                int sum = state.getState().stream().mapToInt(Integer::intValue).sum();
                bySum.merge(sum, statesProbatility.get(state), (v1, v2) -> v1 + v2);
            }
        }
        write(bySum, "distribution_sumConcat");
    }

    private void write(HashMap<Integer, Double> statesByCost, String distribution_sumConcat) {
        if (write) {
            Csv csv = new Csv(getClass().getName(), distribution_sumConcat, costType, strategy);
            logger.info("Writting file          : " + csv.getOutPath());
            csv.add("Score", "Probability");
            for (Integer i : statesByCost.keySet()) {
                double prob = statesByCost.get(i);
                csv.add(i, prob);
            }
            csv.write();
        }
    }

    public HashMap<Integer, Double> getDistribution() {
        return statesByCost;
    }
}
