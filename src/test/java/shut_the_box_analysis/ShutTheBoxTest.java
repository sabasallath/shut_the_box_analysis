package shut_the_box_analysis;

import org.junit.Test;
import shut_the_box_analysis.dag.StrategyType;
import shut_the_box_analysis.dag.states.CostType;
import shut_the_box_analysis.dag.Dag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ShutTheBoxTest {
    private static final double delta = 0.0001;

    @Test
    public void createTransitionTreetest() {
        Dag dag = new Dag(CostType.CONCAT, StrategyType.MIN);
        assertEquals(0.0, dag.getLeaf().getCost(), delta);
        assertEquals(14764.3168, dag.getRoot().getCost(), delta);
    }
}
