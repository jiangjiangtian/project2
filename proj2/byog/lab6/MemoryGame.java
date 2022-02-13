package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private boolean gameOver;
    private boolean playerTurn;
    private Random random;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        random = new Random(seed);
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        if(n < 0) {
            return null;
        }
        char[] target = new char[n];
        for(int i = 0; i < n; i++) {
            int randomnum = random.nextInt(26);
            target[i] = CHARACTERS[randomnum];
        }
        return new String(target);
    }

    //stage 1 -- show , stage 2 -- type
    public void drawFrame(String s, int stage) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(20, 20, s);
        StdDraw.line(0, 35, 40, 35);
        if(stage == 1) {
            StdDraw.text(5, 37, "Round:" + round);
            StdDraw.text(18, 37, "Watch!");
            StdDraw.text(33, 37, ENCOURAGEMENT[1]);
        } else if (stage == 2) {
            StdDraw.text(5, 37, "Round:" + round);
            StdDraw.text(18, 37, "Type!");
            StdDraw.text(33, 37, ENCOURAGEMENT[1]);
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        int len = letters.length();
        for(int i = 0; i < len; i++) {
            drawFrame(letters.substring(i, i + 1), 1);
            StdDraw.pause(1000);
            StdDraw.clear();
            StdDraw.pause(500);
        }
        StdDraw.clear();
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        int i = 0;
        char[] input = new char[n];
        while (true) {
            if(StdDraw.hasNextKeyTyped()) {
                input[i] = StdDraw.nextKeyTyped();
                drawFrame(new String(input), 2);
                i++;
            }
            if(i >= n) {
                break;
            }
        }
        return new String(input);
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        String question = "";
        String input = "";
        round = 1;
        //TODO: Establish Game loop
        while(true) {
            drawFrame("Round: " + round, 1);
            StdDraw.pause(1000);
            question = generateRandomString(round);
            flashSequence(question);
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            drawFrame("", 2);
            input = solicitNCharsInput(round);
            StdDraw.pause(500);
            if(question.equals(input)) {
                round++;
            } else {
                break;
            }
        }
        drawFrame("Game Over! You made it to round:" + round, 2);
    }

}
