package shut_the_box_analysis;


public class Simulate {

    private static final int NB_GAME = 1000;

    public static void main(String[] args) {
        ShutTheBox box = new ShutTheBox();
        int winningCount = 0;
        for (int i = 0; i < NB_GAME; i++) {
            if (box.simulate(false)) winningCount ++;
        }
        System.out.println("Winning Games : " + winningCount + "/" + NB_GAME);
        System.out.println("Winning Ratio : " + ((double) winningCount / (double) NB_GAME) * 100 + " %");
    }

}
