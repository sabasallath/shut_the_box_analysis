package shut_the_box_analysis.dag.states;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public abstract class State {

    private final int dice;
    private double cost;
    TreeSet<Integer> state;
    private List<State> next;
    private List<State> previous;
    private State strategyNext;

    State(TreeSet<Integer> state, int dice) {
        this.state = state;
        this.dice = dice;
        this.next = new LinkedList<>();
        this.previous = new LinkedList<>();
        this.cost = setScore();
    }

    State(TreeSet<Integer> state) {
        this.state = state;
        this.dice = 0;
        this.next = new LinkedList<>();
        this.previous = new LinkedList<>();
        this.cost = setScore();

    }

    State(State chanceState, Integer dice) {
        this(Sets.newTreeSet(chanceState.getState()), dice);
    }

    abstract double setScore();

    public TreeSet<Integer> getState() {
        return state;
    }

    public int dice() {
        return dice;
    }

    public void addNext(State newState) {
        next.add(newState);
    }

    public void addPrevious(State current) {
        previous.add(current);
    }

    public List<State> getNext() {
        return next;
    }

    public List<State> getPrevious() {
        return previous;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost (){
        return cost;
    }

    public void setStrategyNext(State next) {
        this.strategyNext = next;
    }

    public State getStrategyNext() {
        return strategyNext;
    }

    public String stateAndCost() {
        return toString() + ", c = " + String.format("%.2f", cost);
    }

    boolean isRoot() {
        return this.getState().size() == StateConst.CHANCE_STATE_SIZE.get();
    }

    boolean isLeaf() {
        return this.getState().size() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state1 = (State) o;
        return dice == state1.dice &&
                Objects.equal(state, state1.state);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.toString());
    }


    @Override
    public String toString() {
        return state.toString() + "_" + dice();
    }

}
