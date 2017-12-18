package ai;

import java.util.Random;
import gameboard.Board;
import data.Game;

public class Ai {
    // MEMBER VARIABLES
    private static int boardWidth;
    private static int boardHeight;

    private static boolean placed;

    private static Board aiBoard = new Board();
    private static Board aiStrikes = new Board();

    // CONSTRUCTOR
    public Ai() {
        boardWidth = data.DataContainer.getGameboardWidth();
        boardHeight = data.DataContainer.getGameboardHeight();
    }

    // PUBLIC METHODS
    public void draw() {
        // do all actions here
        data.DataContainer.setAllowed(false);
        if (!placed) {
            place();
            placed = true;
        }
        eval();
    }

    public int hit(int x, int y) {
        int field = aiBoard.checkboard(x, y);
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
        /* evaluate next target, fire and handle results */
        int x, y;
        Trace trace = new Trace();

        if (trace.getSize() == 0) {
            Random randomGenerator = new Random();
            x = randomGenerator.nextInt(boardWidth);
            y = randomGenerator.nextInt(boardHeight);
        } else {
            x = 0; // placeholder
            y = 0; // placeholder
        }

        /* Random randomGenerator = new Random();
        x = randomGenerator.nextInt(boardWidth);
        y = randomGenerator.nextInt(boardHeight); */

        int ret = fire(x, y);    // 0: Wasser, 1: Treffer, 2: versenkt
        System.out.println("before switch");
        switch (ret) {
            case 0:
                System.out.println("Just water here :/");
                break;
            case 1:
                trace.addTile(x, y);
                System.out.println("Hit ship!");
                break;
            case 2:
                trace.clear();
                System.out.println("Destroyed ship!");
                break;
        }

        /* util.Tuple[] traceTuple = trace.getTrace();
        trace.getTrace()[0].get(0);
        trace.getTrace()[0].get(1); */
    }

    private int fire(int x, int y) {
        /* fire on other player and handle result */
        return Game.getHit(x, y);
    }

    private void place() {
        /* place ships on Board */
    }
}
