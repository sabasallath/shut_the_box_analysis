package shut_the_box_analysis.states;

import com.google.common.base.Objects;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public abstract class State {

    private final int dice;
    private final List<State> next;
    private final List<State> previous;
    private final Integer score;
    private double cost;
    final TreeSet<Integer> state;
    private State strategyNext;

    State(TreeSet<Integer> state, int dice) {
        this.state = state;
        this.dice = dice;
        this.next = new LinkedList<>();
        this.previous = new LinkedList<>();
        this.score = setScore();
        this.cost = score;
    }

    State(TreeSet<Integer> state) {
        this(state, 0);
    }

    abstract int setScore();

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

    public boolean isRoot() {
        return this.getState().size() == StateConst.CHANCE_STATE_SIZE.get();
    }

    public boolean isLeaf() {
        return this.getState().size() == 0;
    }

    public Integer getScore() {
        return score;
    }

    public boolean hasNext() {
        return next.isEmpty();
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
