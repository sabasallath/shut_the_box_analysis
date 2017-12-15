package shut_the_box_analysis.TransitionDag;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import shut_the_box_analysis.box.Box;
import shut_the_box_analysis.states.State;

public class TransitionDag {

    private final ImmutableMap<Integer, State> map;
    private final static int rootH = new State(Sets.newTreeSet(Box.irange())).hashCode();
    private final static int leafH = new State(Sets.newTreeSet()).hashCode();

    private TransitionDag() {
        this.map = new TransitionDagMutable().getMap();
    }

    private final static class TransitionTreeHolder {
        private final static TransitionDag TRANSITION_TREE = new TransitionDag();
    }

    private static TransitionDag getInstance() {
        return TransitionTreeHolder.TRANSITION_TREE;
    }

    public static State get(int hashcode) {
        return getInstance().map.get(hashcode);
    }

    public static State getRoot() {
        return getInstance().map.get(rootH);
    }

    public static State getLeaf() {
        return getInstance().map.get(leafH);
    }

}
