package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.MouseInfo;
import java.awt.Point;

import java.awt.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        GenerateMainMenu();
        int seed = WaitForInput();
        GeneratingGame.GeneratingGame(seed);
        StdDraw.clear(Color.BLACK);
    }

    private static void GenerateMainMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(40, 24, "CS61B: THE  GAME");
        font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(40, 12, "Quit (Q)");
        StdDraw.text(40, 13, "Load Game (L)");
        StdDraw.text(40, 14, "New Game (N)");
        StdDraw.show();
    }

    private int WaitForInput() {
        while(true) {
            if(StdDraw.hasNextKeyTyped()) {
                char input = StdDraw.nextKeyTyped();
                switch(input) {
                    case 'N':
                        return WaitFOrInputSeed();
                    case 'L':
                        return 0;
                    case 'Q':
                        System.exit(0);
                        break;
                }
            }
        }
    }

    private int WaitFOrInputSeed() {
        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        StdDraw.text(40, 16, "Please enter a seed that you want(press S to end the input):");
        char[] input = new char[20];
        int i = 0;
        while(true) {
            if(StdDraw.hasNextKeyTyped()) {
                input[i] = StdDraw.nextKeyTyped();
                drawFrame(new String(input));
                i++;
                if(input[i - 1] == 'S') {
                    break;
                }
            }
        }
        char[] input2 = new char[i - 1];
        for(int j = 0; j < input2.length; j++) {
            input2[j] = input[j];
        }
        int seed = Integer.parseInt(new String(input2));
        return seed;
    }

    private void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        StdDraw.text(40, 16, "Please enter a seed that you want(press S to end the input):");
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(40, 15, s);
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }
}
