package shut_the_box_analysis;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import shut_the_box_analysis.TransitionDag.TransitionDag;
import shut_the_box_analysis.states.State;

public class AnalysisWinningDepth {

    private final Multimap<Integer, State> statesByLevel;
    private final String res;

    public AnalysisWinningDepth() {
        statesByLevel = MultimapBuilder.hashKeys().linkedHashSetValues().build();
        exploreChanceNode(0, TransitionDag.getRoot());
        res = sumByLevel() + winningLeafByLevel();
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

    private String sumByLevel() {
        StringBuilder sb = new StringBuilder("Sum by level\n");
        for (Integer i : statesByLevel.keySet()) {
            double sum = statesByLevel.get(i).stream().mapToDouble(State::getCost).sum();
            sb.append("level ").append(i)
                    .append(", sum = ").append(sum)
                    .append(", mean = ").append(sum / statesByLevel.get(i).size())
                    .append(", size = ").append(statesByLevel.get(i).size())
                    .append('\n');
        }
        return sb.toString();
    }

    private String winningLeafByLevel() {
        StringBuilder sb = new StringBuilder("Winning leaf by level\n");
        State leaf = TransitionDag.getLeaf();
        for (Integer i : statesByLevel.keySet()) {
            int sum = statesByLevel.get(i).stream().mapToInt(e -> e.equals(leaf) ? 1 : 0).sum();
            sb.append("level ").append(i)
                    .append(", sum = ")
                    .append(sum).append(", mean = ").append(sum / statesByLevel.get(i).size())
                    .append(", size = ").append(statesByLevel.get(i).size())
                    .append('\n');
        }
        return sb.toString();
    }

    public String getRes() {
        return res;
    }

    String format(int i) {
        return String.format("%2d", i);
    }

    String format(double d) {
        return String.format("%.4d", d);
    }

}
