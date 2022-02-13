package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.awt.Color;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static TERenderer ter;
    private static TETile[][] hexagon;

    public static void setInitial (int s) {
        ter = new TERenderer();
        ter.initialize(11 * s - 1, 10 * s);
        hexagon = CreateTETile2DArray(s);
    }
    /** adds a hexagon of side length s to a given position in the world */
    public static void addHexagon(int s, int startX, int startY, TETile item) {
        FillInTheHexagon(hexagon, item, s, startX, startY);

        ter.renderFrame(hexagon);
    }

    private static void FillInTheHexagon(TETile[][] world, TETile item, int size, int startX, int startY) {
        for(int i = 1; i <= size; i++) {
            for(int j = 1; j <= size + 2 * i - 2; j++) {
                int blank = (3 * size - 2 - (size + 2 * i - 2)) / 2;
                //Random colors
                //item = TETile.colorVariant(item, 32, 32, 32, new Random());
                world[startX + j - 1 + blank][startY + i - 1] = item;
            }
        }

        for(int i = size; i > 0; i--) {
            for(int j = 1; j <= size + 2 * i - 2; j++) {
                int blank = (3 * size - 2 - (size + 2 * i - 2)) / 2;
                //Random colors
                //item = TETile.colorVariant(item, 32, 32, 32, new Random());
                world[startX + j - 1 + blank][startY + size + size - i] = item;
            }
        }
    }

    private static TETile[][] CreateTETile2DArray(int s) {
        TETile[][] world;
        world = new TETile[11 * s][10 * s];
        /* First : set all the items to the nothing.*/
        for(int i = 0; i < world.length; i++) {
            for(int j = 0; j < world[0].length; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        return world;
    }

    public static void TesselateHexagons(int size) {
        setInitial(size);
        int startX = 5 * size - 3;
        int startY = 0;
        for(int i = 0; i < 4; i++) {
            TesselateTowardsLeftAbove(startX, startY, size);
            TesselateTowardsRightAbove(startX, startY, size);
            startY += 2 * size;
        }
        addHexagon(size, startX, startY, setTile());
    }

    public static TETile setTile() {
        TETile tile = Tileset.WATER;
        Random RANDOM = new Random();
        int randomnum = RANDOM.nextInt(11);
        switch(randomnum) {
            case 0:
                tile = Tileset.FLOWER;
                break;
            case 1:
                tile = Tileset.WALL;
                break;
            case 2:
                tile = Tileset.FLOOR;
                break;
            case 3:
                tile = Tileset.GRASS;
                break;
            case 4:
                tile = Tileset.LOCKED_DOOR;
                break;
            case 5:
                tile = Tileset.MOUNTAIN;
                break;
            case 6:
                tile = Tileset.PLAYER;
                break;
            case 7:
                tile = Tileset.SAND;
                break;
            case 8:
                tile = Tileset.TREE;
                break;
            case 9:
                tile = Tileset.UNLOCKED_DOOR;
                break;
            case 10:
                tile = Tileset.WATER;
                break;
        }
        return tile;
    }

    public static void TesselateTowardsLeftAbove(int startX, int startY, int size) {
        while(startX >= 0 && startX < 11 * size - 5 && startY + 2 * size < 10 * size) {
            TETile tile = setTile();
            addHexagon(size, startX, startY, tile);
            startX = startX - 2 * size + 1;
            startY = startY + size;
        }
    }

    public static void TesselateTowardsRightAbove(int startX, int startY, int size) {
        startX = startX + 2 * size - 1;
        startY = startY + size;
        while(startX >= 0 && startX + 2 * size - 2 < 11 * size - 5 && startY + 2 * size < 10 * size) {
            TETile tile = setTile();
            addHexagon(size, startX, startY, tile);
            startX = startX + 2 * size - 1;
            startY = startY + size;
        }
    }

    @Test
    public void testsetTile() {
        TETile expected = Tileset.FLOWER;
        TETile actual = setTile();
        assertEquals(expected, actual);
        TETile expected1 = Tileset.WALL;
        TETile actual1 = setTile();
        assertEquals(expected1, actual1);
        TETile expected2 = Tileset.SAND;
        TETile actual2 = setTile();
        assertEquals(expected2, actual2);
        TETile expected3 = Tileset.WATER;
        TETile actual3 = setTile();
        assertEquals(expected3, actual3);
        Random random = new Random();
        TETile expected4 = Tileset.WATER;
        TETile actual4 = setTile();
        assertEquals(expected4, actual4);
    }

    public static void main(String[] args) {
        TesselateHexagons(4);
        //addHexagon(3, 13, 13, Tileset.WALL);
        //addHexagon(3, 13, 24, Tileset.WATER);
    }
}
