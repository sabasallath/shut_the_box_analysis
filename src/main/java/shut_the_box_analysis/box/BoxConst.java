package shut_the_box_analysis.box;

public enum BoxConst {

    MIN(1),
    MAX(9),
    NB_ELEMENTS(MAX.get() - MIN.get() + 1);

    private final int size;

    BoxConst(int size) {
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
