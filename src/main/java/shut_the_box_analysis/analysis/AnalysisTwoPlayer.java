package shut_the_box_analysis.analysis;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shut_the_box_analysis.box.Box;
import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.dice.DiceConst;
import shut_the_box_analysis.io.Csv;
import shut_the_box_analysis.states.CostType;

import java.util.HashMap;
import java.util.List;

public class AnalysisTwoPlayer {

    private final static Logger logger = LoggerFactory.getLogger(AnalysisTwoPlayer.class);
    private final CostType costType;
    private final StrategyType strategy;
    private HashMap<Integer, Double> probabilityToWin;

    public AnalysisTwoPlayer(StrategyType strategy) {
        this.probabilityToWin = Maps.newHashMap();
        this.costType = CostType.BEAT;
        this.strategy = strategy;
        for (Integer i : scoreToBeat()) {
            probabilityToWin.put(i, analyseBeatScore(i));
        }
        write();
    }

    private Double analyseBeatScore(Integer scoreToBeat) {
        Dag dag = new Dag(costType, strategy, scoreToBeat);
        HashMap<Integer, Double> distribution = new AnalysisDistribution(dag, false).getDistribution();
        return distribution.getOrDefault(1, 0.0);
    }

    private ImmutableList<Integer> scoreToBeat() {
        Integer maxScore = Box.irange().stream().mapToInt(Integer::intValue).sum() - DiceConst.MIN.get();

        List<Integer> scoreToBeat = Lists.newArrayList();
        for (int i = 0; i <= maxScore; i++) {
            scoreToBeat.add(i);
        }

        return ImmutableList.copyOf(scoreToBeat);
    }

    private void write() {
        Csv csv = new Csv(getClass().getName(), "distribution", costType, strategy);
        logger.info("Writting file          : " + csv.getOutPath());
        csv.add("Score", "Probability to beat player 2");
        for (Integer i : probabilityToWin.keySet()) {
            double prob = probabilityToWin.get(i);
            csv.add(i, prob);
        }
        csv.write();
    }
}
