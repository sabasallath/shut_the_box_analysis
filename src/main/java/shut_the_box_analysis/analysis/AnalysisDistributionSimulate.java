package shut_the_box_analysis.analysis;

import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.states.CostType;
import shut_the_box_analysis.states.State;
import shut_the_box_analysis.io.Csv;
import shut_the_box_analysis.simulation.Simulation;

import java.util.HashMap;
import java.util.Optional;

public class AnalysisDistributionSimulate {

    private final static int NB_GAME = 100000;
    private final CostType costType;
    private final StrategyType strategy;
    private final HashMap<Integer, Integer> finalStates;
    private final Simulation simulation;

    public AnalysisDistributionSimulate(Dag dag) {
        this.costType = dag.getFactory().getType();
        this.strategy = dag.getStrategy();
        finalStates = new HashMap<>();
        simulation = new Simulation(dag);
        distribution();
        write();
    }

    private void distribution() {
        for (int i = 0; i < NB_GAME; i++) {
            State state = simulation.run();
            finalStates.merge(state.getScore(), 1, (v1, v2) -> v1 + v2);
        }
    }

    private void write() {
        Csv csv = new Csv(getClass().getName(), "distribution", costType, strategy);
        csv.add("Sum", "Quantity");

        for (Integer i : finalStates.keySet()) {
            Integer qty = finalStates.get(i);
            csv.add(i, ((double) qty / (double) NB_GAME));
        }
        csv.write();
    }

}
