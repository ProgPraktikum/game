package ai;

import data.DataContainer;


public class AI {
    // MEMBER VARIABLES
    private DataContainer settings = new DataContainer();
    private static int boardWidth;
    private static int boardHeight;

    private static int aiBoard[][];
    private static int aiStrikes[][];

    // CONSTRUCTOR
    public AI() {
        boardWidth = settings.getGameboardWidth();
        boardHeight = settings.getGameboardHeight();
        aiBoard = new int[boardWidth][boardHeight];
        aiStrikes = new int[boardWidth][boardHeight];
    }

    // PUBLIC METHODS
    public void draw() {
        // do all actions here
    }

    public int hit(int x, int y) {
        int field = aiBoard[x][y];
        switch (field) {
            case 0:         // Wasser
                return 0;
            case 2:         // Schiff
                return 2;
            case 3:         // Bereits versenktes Schiff
                return 3;
            default:
                return -1;  // To prevent compiler warnings and for the case of errors
        }
    }

    // PRIVATE METHODS
    private void eval() {
        /* evaluate next target */
    }

    private void fire() {
        /* fire on other player and handle result */
    }

    private void place() {
        /* place ships on Board */
    }
}
