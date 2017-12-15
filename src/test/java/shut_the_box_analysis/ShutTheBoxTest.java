package shut_the_box_analysis;

import org.junit.Test;
import shut_the_box_analysis.TransitionDag.TransitionDag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ShutTheBoxTest {
    private static final double delta = 0.0001;

    @Test
    public void createTransitionTreetest() {
        assertEquals(0.0, TransitionDag.getLeaf().getCost(), delta);
        assertEquals(14764.3168, TransitionDag.getRoot().getCost(), delta);
    }
}
