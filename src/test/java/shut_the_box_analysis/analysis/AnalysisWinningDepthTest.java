package shut_the_box_analysis.analysis;


import org.junit.Test;
import shut_the_box_analysis.TestConst;
import shut_the_box_analysis.dag.Dag;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.dag.states.CostType;

import static org.junit.Assert.assertEquals;

public class AnalysisWinningDepthTest {

    @Test
    public void winningDepthConcatMinTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.CONCAT, StrategyType.MIN));
        assertEquals(48474591.9287, analysis.getSumByLevelControl(), TestConst.DELTA);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }

    @Test
    public void winningDepthConcatMaxTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.CONCAT, StrategyType.MAX));
        assertEquals(67836598.8146, analysis.getSumByLevelControl(), TestConst.DELTA);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }

    @Test
    public void winningDepthSumMinTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.SUM, StrategyType.MIN));
        assertEquals(127713.3377, analysis.getSumByLevelControl(), TestConst.DELTA);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }

    @Test
    public void winningDepthSumMaxTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.SUM, StrategyType.MAX));
        assertEquals(55751.9386, analysis.getSumByLevelControl(), TestConst.DELTA);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }

    @Test
    public void winningDepthMinMinTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.WIN_LOOSE, StrategyType.MIN));
        assertEquals(151.3676, analysis.getSumByLevelControl(), TestConst.DELTA);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }

    @Test
    public void winningDepthMinMaxTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.WIN_LOOSE, StrategyType.MAX));
        assertEquals(760.7884, analysis.getSumByLevelControl(), TestConst.DELTA);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }
}
