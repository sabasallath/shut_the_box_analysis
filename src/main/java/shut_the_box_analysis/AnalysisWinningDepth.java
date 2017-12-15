package shut_the_box_analysis;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.io.Csv;
import shut_the_box_analysis.dag.states.State;

public class AnalysisWinningDepth {

    private final Multimap<Integer, State> statesByLevel;
    private final Dag dag;
    private final CostType costType;


    public AnalysisWinningDepth(Dag dag) {
        this.dag = dag;
        this.costType = dag.getFactory().getType();
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
        State next = current.getMinNext();
        if (next != null) {
            exploreChanceNode(level + 1, next);
        }
    }

    private void sumByLevel() {
        Csv csv = new Csv(getClass().getName(), "sumByLevel", costType);
        csv.add("Level", "Sum", "Mean", "Size");
        for (Integer i : statesByLevel.keySet()) {
            Double sum = statesByLevel.get(i).stream().mapToDouble(State::getCost).sum();
            int size = statesByLevel.get(i).size();
            double mean = sum / size;
            csv.add(i, sum, mean, size);
        }
        csv.write();
    }

    private void winningLeafByLevel() {
        Csv csv = new Csv(getClass().getName(), "winningLeafByLevel", costType);
        csv.add("Level", "Sum", "Mean", "Size");

        State leaf = dag.getLeaf();
        for (Integer i : statesByLevel.keySet()) {
            int sum = statesByLevel.get(i).stream().mapToInt(e -> e.equals(leaf) ? 1 : 0).sum();
            int size = statesByLevel.get(i).size();
            int mean = sum / size;
            csv.add(i, sum, mean, size);
        }
        csv.write();
    }

}
