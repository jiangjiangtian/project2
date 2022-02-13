package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

public class Test {
    public static void main(String[] args) {
        int seed = 10;
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.flashSequence(game.generateRandomString(5));
        StdDraw.clear();
        int i = 0;
        char[] keyType = new char[5];
        while(true) {
            if(StdDraw.hasNextKeyTyped()) {
                keyType[i] = StdDraw.nextKeyTyped();
                game.drawFrame(new String(keyType), 1);
                //StdDraw.clear();
                i++;
            }
            if (i >= 5) {
                break;
            }
        }
    }
}
