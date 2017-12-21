package shut_the_box_analysis;

import shut_the_box_analysis.analysis.AnalysisDistribution;
import shut_the_box_analysis.analysis.AnalysisWinningDepth;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.dag.Dag;

public class Analyse {

    public static void main(String[] args) {
        for (CostType costType : CostType.values()) {
            for (StrategyType strategyType : StrategyType.values()) {
                Dag dag = new Dag(costType, strategyType);
                new AnalysisWinningDepth(dag);
                new AnalysisDistribution(dag);
            }
        }

    }

}
