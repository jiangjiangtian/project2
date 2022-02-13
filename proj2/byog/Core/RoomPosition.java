package byog.Core;

public class RoomPosition {
    int startX;
    int startY;
    int width;
    int height;
    boolean ConnectUpwardHallway;
    boolean ConnectDownwardHallway;
    boolean ConnectLeftHallway;
    boolean ConnectRightHallway;

    public RoomPosition(int x, int y, int w, int h) {
        startX = x;
        startY = y;
        width = w;
        height = h;
        ConnectDownwardHallway = false;
        ConnectUpwardHallway = false;
        ConnectLeftHallway = false;
        ConnectRightHallway = false;
    }
}
