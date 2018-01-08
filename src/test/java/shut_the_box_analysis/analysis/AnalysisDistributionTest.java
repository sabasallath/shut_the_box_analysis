package shut_the_box_analysis.analysis;

import org.junit.Test;
import shut_the_box_analysis.TestConst;
import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.states.CostType;

import static org.junit.Assert.assertEquals;

public class AnalysisDistributionTest {

    @Test
    public void distributionTest() {
        AnalysisDistribution analysis = new AnalysisDistribution(new Dag(CostType.SUM, StrategyType.MIN));
        assertEquals(0.9999, analysis.getTotalProb(), TestConst.DELTA);
    }
}
