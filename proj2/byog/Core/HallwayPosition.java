package byog.Core;

public class HallwayPosition {
    public int atX;
    public int len;
    public String direction;
    public String GoTo;
    public int atY;

    public HallwayPosition(int x, int y, String r, int l, String go) {
        atX = x;
        atY = y;
        direction = r;
        len = l;
        GoTo = go;
    }

    public static boolean IsTheSameVerticalHallway(HallwayPosition h1, HallwayPosition h2) {
        if (h1.direction.equals("Vertical") && h2.direction.equals("Vertical")) {
           if(h1.atX == h2.atX && Math.abs(h1.atY - h2.atY) == h1.len - 1 && h1.len == h2.len) {
               return true;
           }
        }
        return false;
    }

    public static boolean IsTheSameLevelHallway(HallwayPosition h1, HallwayPosition h2) {
        if (h1.direction.equals("Level") && h2.direction.equals("Level")) {
            if(h1.atY == h2.atY && Math.abs(h1.atX - h2.atX) == h1.len - 1 && h1.len == h2.len) {
                return true;
            }
        }
        return false;
    }
}
