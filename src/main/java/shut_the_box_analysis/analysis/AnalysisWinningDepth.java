package shut_the_box_analysis.analysis;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.states.CostType;
import shut_the_box_analysis.io.Csv;
import shut_the_box_analysis.states.State;

public class AnalysisWinningDepth {

    private final Multimap<Integer, State> statesByLevel;
    private final Dag dag;
    private final CostType costType;
    private final StrategyType strategy;

    public Double getSumByLevelControl() {
        return sumByLevelControl;
    }

    public int getWinningLeafByLevelControl() {
        return winningLeafByLevelControl;
    }

    private Double sumByLevelControl;
    private int winningLeafByLevelControl;


    public AnalysisWinningDepth(Dag dag) {
        // For test purpose
        this.sumByLevelControl = 0.0;
        this.winningLeafByLevelControl = 0;

        this.dag = dag;
        this.costType = dag.getFactory().getType();
        this.strategy = dag.getStrategy();
        this.statesByLevel = MultimapBuilder.hashKeys().linkedHashSetValues().build();
        exploreChanceNode(0, dag.getRoot());
        sumByLevel();
        winningLeafByLevel();
    }

    private void exploreChanceNode(int level, State current) {
        statesByLevel.put(level, current);
        for (State state : current.getNext()) {
            exploreDecisionNode( level + 1, state);
        }
    }

    private void exploreDecisionNode(int level, State current) {
        statesByLevel.put(level, current);
        State next = current.getStrategyNext();
        if (next != null) {
            exploreChanceNode(level + 1, next);
        }
    }

    private void sumByLevel() {
        Csv csv = new Csv(getClass().getName(), "sumByLevel", costType, strategy);
        csv.add("Level", "Sum", "Mean");
        for (Integer i : statesByLevel.keySet()) {
            Double sum = statesByLevel.get(i).stream().mapToDouble(State::getCost).sum();
            sumByLevelControl += sum;
            int size = statesByLevel.get(i).size();
            double mean = sum / size;
            csv.add(i, sum, mean);
        }
        csv.write();
    }

    private void winningLeafByLevel() {
        Csv csv = new Csv(getClass().getName(), "winningLeafByLevel", costType, strategy);
        csv.add("Level", "Sum", "Size");

        State leaf = dag.getLeaf();
        for (Integer i : statesByLevel.keySet()) {
            int sum = statesByLevel.get(i).stream().mapToInt(e -> e.equals(leaf) ? 1 : 0).sum();
            winningLeafByLevelControl += sum;
            int size = statesByLevel.get(i).size();
            csv.add(i, sum, size);
        }
        csv.write();
    }

}
