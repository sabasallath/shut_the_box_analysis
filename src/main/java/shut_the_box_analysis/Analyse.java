package shut_the_box_analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shut_the_box_analysis.analysis.AnalysisDistribution;
import shut_the_box_analysis.analysis.AnalysisDistributionSimulate;
import shut_the_box_analysis.analysis.AnalysisWinningDepth;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.states.CostType;
import shut_the_box_analysis.dag.Dag;

public class Analyse {

    private final static Logger logger = LoggerFactory.getLogger(Analyse.class);

    public static void main(String[] args) {

        logger.info("Starting analyse");

        for (CostType costType : CostType.values()) {
            for (StrategyType strategyType : StrategyType.values()) {
                Dag dag = new Dag(costType, strategyType);
                new AnalysisWinningDepth(dag);
                new AnalysisDistribution(dag);
                new AnalysisDistributionSimulate(dag);
            }
        }
    }
}
