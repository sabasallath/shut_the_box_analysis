package shut_the_box_analysis.analysis;

import com.google.common.collect.Maps;
import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.states.CostType;
import shut_the_box_analysis.states.State;
import shut_the_box_analysis.io.Csv;
import shut_the_box_analysis.simulation.Simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AnalysisDistributionSimulate {

    private final static int NB_GAME = 100000;
    private final CostType costType;
    private final StrategyType strategy;
    private final HashMap<Integer, Integer> finalStates;
    private final Simulation simulation;
    private HashMap<Integer, Integer> finalStatesSumConcat;

    public AnalysisDistributionSimulate(Dag dag) {
        this.costType = dag.getFactory().getType();
        this.strategy = dag.getStrategy();
        finalStates = new HashMap<>();
        if (costType == CostType.CONCAT) {
            finalStatesSumConcat = Maps.newHashMap();
        }
        simulation = new Simulation(dag);
        distribution();
        write(finalStates, "distribution");
        if (costType == CostType.CONCAT) {
            write(finalStatesSumConcat, "distribution_sumConcat");
        }
    }

    private void distribution() {
        for (int i = 0; i < NB_GAME; i++) {
            State state = simulation.run();
            finalStates.merge(state.getScore(), 1, (v1, v2) -> v1 + v2);
            if (costType == CostType.CONCAT) {
                distributionSumConcat(state);
            }
        }
    }

    /**
     * To compare the use of the strategy concat with the sum rule.
     */
    private void distributionSumConcat(State state) {
        int sum = state.getState().stream().mapToInt(Integer::intValue).sum();
        finalStatesSumConcat.merge(sum, 1, (v1, v2) -> v1 + v2);
    }

    private void write(HashMap<Integer, Integer> map, String filename) {
        Csv csv = new Csv(getClass().getName(), filename, costType, strategy);
        csv.add("Score", "Quantity/Total Quantity");

        for (Integer i : map.keySet()) {
            Integer qty = map.get(i);
            csv.add(i, ((double) qty / (double) NB_GAME));
        }
        csv.write();
    }

}
