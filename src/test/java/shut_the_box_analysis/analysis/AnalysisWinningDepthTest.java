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
        assertEquals(48513649.5203, analysis.getSumByLevelControl(), TestConst.delta);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }

    @Test
    public void winningDepthConcatMaxTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.CONCAT, StrategyType.MAX));
        assertEquals(67847185.7282, analysis.getSumByLevelControl(), TestConst.delta);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }

    @Test
    public void winningDepthSumMinTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.SUM, StrategyType.MIN));
        assertEquals(130839.7193, analysis.getSumByLevelControl(), TestConst.delta);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }

    @Test
    public void winningDepthSumMaxTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.SUM, StrategyType.MAX));
        assertEquals(55749.3212, analysis.getSumByLevelControl(), TestConst.delta);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }

    @Test
    public void winningDepthMinMinTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.WIN_LOOSE, StrategyType.MIN));
        assertEquals(151.3676, analysis.getSumByLevelControl(), TestConst.delta);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }

    @Test
    public void winningDepthMinMaxTest() {
        AnalysisWinningDepth analysis = new AnalysisWinningDepth(new Dag(CostType.WIN_LOOSE, StrategyType.MAX));
        assertEquals(760.7884, analysis.getSumByLevelControl(), TestConst.delta);
        assertEquals(5, analysis.getWinningLeafByLevelControl());
    }
}
