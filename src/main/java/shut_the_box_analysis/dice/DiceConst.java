package shut_the_box_analysis.dice;

public enum DiceConst {

    SIDES(6),
    MIN(2),
    MAX(MIN.get() * SIDES.get()),
    ELEMENTS(MAX.get() - MIN.get() + 1);

    private final int size;

    DiceConst(int size) {
        this.size = size;
    }

    public int get() {
        return size;
    }

    @Override
    public String toString(){
        return Integer.toString(size);
    }
}
