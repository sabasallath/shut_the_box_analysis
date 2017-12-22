package shut_the_box_analysis.states;

import com.google.common.collect.Sets;

import java.util.TreeSet;

public class StateFactory {

    private final CostType costType;

    public StateFactory(CostType costType) {
        this.costType = costType;
    }

    public State state(TreeSet<Integer> state, int dice) {
        switch(costType) {
            case SUM:
                return new StateSum(state, dice);
            case CONCAT:
                return new StateConcat(state, dice);
            case WIN_LOOSE:
                return new StateWinLoose(state, dice);
            case REACH_ONE:
                return new StateReachOne(state, dice);
            default:
                throw new RuntimeException("No more costType");
        }
    }

    public State state(TreeSet<Integer> state) {
        switch(costType) {
            case SUM:
                return new StateSum(state);
            case CONCAT:
                return new StateConcat(state);
            case WIN_LOOSE:
                return new StateWinLoose(state);
            case REACH_ONE:
                return new StateReachOne(state);
            default:
                throw new RuntimeException("No more costType");
        }
    }

    public State state(State chanceState, Integer dice) {
        switch(costType) {
            case SUM:
                return new StateSum(Sets.newTreeSet(chanceState.getState()), dice);
            case CONCAT:
                return new StateConcat(Sets.newTreeSet(chanceState.getState()), dice);
            case WIN_LOOSE:
                return new StateWinLoose(Sets.newTreeSet(chanceState.getState()), dice);
            case REACH_ONE:
                return new StateReachOne(Sets.newTreeSet(chanceState.getState()), dice);
            default:
                throw new RuntimeException("No more costType");
        }
    }

    public CostType getType() {
        return costType;
    }
}