package byog.Core;

import java.awt.*;
import java.util.Random;
import byog.TileEngine.*;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Test;
import static org.junit.Assert.*;
/** Requests:The world must be a 2D grid, drawn using our tile engine. The tile engine is described in lab5.
 The world must be pseudorandomly generated. Pseudorandomness is discussed in lab 5.
 The generated world must include rooms and hallways, though it may also include outdoor spaces.
 At least some rooms should be rectangular, though you may support other shapes as well.
 Your game must be capable of generating hallways that include turns (or equivalently, straight hallways that intersect).
 The world should contain a random number of rooms and hallways.
 The locations of the rooms and hallways should be random.
 The width and height of rooms should be random.
 The length of hallways should be random.
 Rooms and hallways must have walls that are visually distinct from floors. Walls and floors should be visually distinct from unused spaces.
 Rooms and hallways should be connected, i.e. there should not be gaps in the floor between adjacent rooms or hallways.
 The world should be substantially different each time, i.e. you should not have the same basic layout with easily predictable features
 *  */
public class GeneratingGame {
    private Random random;
    public TETile[][] world;
    public static int seed;

    public GeneratingGame(int height, int width, int s) {
        seed = s;
        random = new Random(seed);
        world = new TETile[width][height];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                world[j][i] = Tileset.NOTHING;
            }
        }
    }
    public static void GeneratingGame(int seed) {
        GeneratingGame ggame = new GeneratingGame(30, 80, seed);
        TERenderer show = new TERenderer();
        show.initialize(ggame.world.length,ggame.world[0].length);
        int startX, startY, height, width;
        int i = 0;
        int sum = RandomUtils.uniform(ggame.random, 50);
        if(sum < 7) {
            sum += 10;
        }
        Room[] rooms = new Room[sum];
        while(i < sum) {
            startX = RandomUtils.uniform(ggame.random, 0, ggame.world.length);
            startY = RandomUtils.uniform(ggame.random, 0, ggame.world[0].length);
            height = Room.getHeight();
            width = Room.getWidth();
            if(Room.IsThereARoom(ggame.world, Tileset.FLOOR, startX, startY, height, width)) {
                continue;
            }
            rooms[i] = new Room();
            rooms[i].addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);
            //Hallway.addVerticalHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[i]);
            i++;
        }
        for(i = 0; i < sum; i++) {
            for(int j = 0; j < sum; j++) {
                if(!rooms[i].IsConnected(rooms[j])) {
                    Hallway.addVerticalHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[i], rooms[j]);
                    Hallway.addLevelHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[i], rooms[j]);
                }
            }
        }
        /*for(i = 0; i < sum; i++) {
            for(int j = i + 1; j < sum; j++) {
                if(!rooms[i].IsConnected(rooms[j])) {
                    //Hallway.addVerticalHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[i], rooms[j]);
                    Hallway.addLevelHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[i], rooms[j]);
                }
            }
        }*/
        for(i = 0; i < sum; i++) {
            if(rooms[i].IsUnconnected()) {
                Hallway.ConnectRoomHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[i], rooms);
            }
        }
        int RandomNum = RandomUtils.uniform(ggame.random, 0, rooms.length);
        Hallway.ConnectRoomHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[RandomNum], rooms);

        for(i = 0; i < sum - 1; i++) {
            for(int j = i + 1; j < sum; j++) {
                if(Room.IfOnlyConnectedLevelOneAnother(rooms[i], rooms[j])) {
                    Hallway.ConnectRoomHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[i], rooms);
                    Hallway.ConnectRoomHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[j], rooms);
                } else if (Room.IfOnlyConnectedVerticalOneAnother(rooms[i], rooms[j])) {
                    Hallway.ConnectRoomHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[i], rooms);
                    Hallway.ConnectRoomHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[j], rooms);
                }
            }
        }

        for(i = 0; i < sum; i++) {
            if(rooms[i].IsUnconnected()) {
                for(int j = 0; j < sum; j++) {
                    rooms[i].FindWayToAnotherRoom(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[j]);
                }
                break;
            }
        }
        ggame.InteractWithUser(show);
        //show.renderFrame(ggame.world);
    }

    private void InteractWithUser(TERenderer show) {
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        while(true) {
            double PointX = StdDraw.mouseX();
            double PointY = StdDraw.mouseY();
            show.renderFrame(this.world);
            StdDraw.clear(Color.BLACK);
            StdDraw.text(10, 28, "mouseX = " + PointX + " mouseY = " + PointY);
            StdDraw.show();
            StdDraw.pause(100);
        }
    }

    /*public static void main(String[] args) {
        GeneratingGame ggame = new GeneratingGame(40, 36, 13);
        TERenderer show = new TERenderer();
        show.initialize(ggame.world[0].length,ggame.world.length);
        Room.addRoom(ggame.world, 5, ggame.world[0].length - 2, Tileset.FLOOR, 1 , 3);
        show.renderFrame(ggame.world);
    }*/
}
