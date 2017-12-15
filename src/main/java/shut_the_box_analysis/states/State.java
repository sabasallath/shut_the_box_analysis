package shut_the_box_analysis.states;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import shut_the_box_analysis.box.Box;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class State {

    private final int dice;
    private double cost;
    private TreeSet<Integer> state;
    private List<State> next;
    private List<State> previous;
    private State minNext;

    public State(TreeSet<Integer> state, int dice) {
        this.state = state;
        this.dice = dice;
        this.next = new LinkedList<>();
        this.previous = new LinkedList<>();
        this.cost = setScore();
    }

    public State(TreeSet<Integer> state) {
        this.state = state;
        this.dice = 0;
        this.next = new LinkedList<>();
        this.previous = new LinkedList<>();
        this.cost = setScore();
    }

    public State(State chanceState, Integer dice) {
        this(Sets.newTreeSet(chanceState.getState()), dice);
    }

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

    private double setScore() {
        StringBuilder sb = new StringBuilder();
	    state.forEach(sb::append);
        String s = sb.toString();
        return s.equals("") ? 0.0 : Double.parseDouble(s);
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public void setMinNext(State next) {
        this.minNext = next;
    }

    public State getMinNext() {
        return minNext;
    }

    @Override
    public String toString() {
        return state.toString() + "_" + dice();
    }

    public String stateAndCost() {
        return toString() + ", cost = " + String.format("%.2f", cost);
    }
}
