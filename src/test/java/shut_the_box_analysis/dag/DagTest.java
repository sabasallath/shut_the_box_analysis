package shut_the_box_analysis.dag;

import org.junit.Test;
import shut_the_box_analysis.TestConst;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.dag.Dag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DagTest {

    @Test
    public void costConcatMinTest() {
        Dag dag = new Dag(CostType.CONCAT, StrategyType.MIN);
        assertEquals(0.0, dag.getLeaf().getCost(), TestConst.delta);
        assertEquals(14764.3168, dag.getRoot().getCost(), TestConst.delta);
    }

    @Test
    public void costConcatMaxTest() {
        Dag dag = new Dag(CostType.CONCAT, StrategyType.MAX);
        assertEquals(0.0, dag.getLeaf().getCost(), TestConst.delta);
        assertEquals(104798.6480, dag.getRoot().getCost(), TestConst.delta);
    }

    @Test
    public void costSumMinTest() {
        Dag dag = new Dag(CostType.SUM, StrategyType.MIN);
        assertEquals(0.0, dag.getLeaf().getCost(), TestConst.delta);
        assertEquals(11.3503, dag.getRoot().getCost(), TestConst.delta);
    }

    @Test
    public void costSumMaxTest() {
        Dag dag = new Dag(CostType.SUM, StrategyType.MAX);
        assertEquals(0.0, dag.getLeaf().getCost(), TestConst.delta);
        assertEquals(24.3448, dag.getRoot().getCost(), TestConst.delta);
    }

    @Test
    public void costMinMinTest() {
        Dag dag = new Dag(CostType.WIN_LOOSE, StrategyType.MIN);
        assertEquals(1.0, dag.getLeaf().getCost(), TestConst.delta);
        assertEquals(0.0097, dag.getRoot().getCost(), TestConst.delta);
    }

    @Test
    public void costMinMaxTest() {
        Dag dag = new Dag(CostType.WIN_LOOSE, StrategyType.MAX);
        assertEquals(1.0, dag.getLeaf().getCost(), TestConst.delta);
        assertEquals(0.07143, dag.getRoot().getCost(), TestConst.delta);
    }
}
