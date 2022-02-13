package byog.Core;

import java.util.Random;
import byog.TileEngine.*;

public class Hallway {
    private static Random random = new Random(GeneratingGame.seed);


    public static void addVerticalHallway(TETile[][] world, TETile floor, TETile wall, Room room1, Room room2) {
        if(room1.findVerticalRoomUpwards(world, floor, wall, room2)) {
            //Fill the floor
            for(int i = room1.hallwayPosition[0].atY; i < room1.hallwayPosition[0].len + room1.hallwayPosition[0].atY; i++) {
                world[room1.hallwayPosition[0].atX][i] = floor;
            }
            //Fill the wall
            wrapHallway(world, wall, floor, room1.hallwayPosition[0]);
            room1.roomPosition.ConnectUpwardHallway = true;
            room2.roomPosition.ConnectDownwardHallway = true;

            /*if(connectedRoom != null) {
                connectedRoom.roomPosition.ConnectDownwardHallway = true;
            }*/
        }
        if(room1.findVerticalRoomDownwards(world, floor, wall, room2)) {
            for(int i = room1.hallwayPosition[1].atY; i >= room1.hallwayPosition[1].atY - room1.hallwayPosition[1].len; i--) {
                world[room1.hallwayPosition[1].atX][i] = floor;
            }
            //Fill the wall
            wrapHallway(world, wall, floor, room1.hallwayPosition[1]);
            room1.roomPosition.ConnectDownwardHallway = true;
            room2.roomPosition.ConnectUpwardHallway = true;
            /*if(connectedRoom != null) {
                connectedRoom.roomPosition.ConnectUpwardHallway = true;
            }*/
        }
    }

    public static void addLevelHallway(TETile[][] world, TETile floor, TETile wall, Room room1, Room room2) {
        /*for(int i = startX; i <= endX; i++) {
            world[i][atY] = tile;
        }
        wrapHallway(world, tile, );*/
        if (room1.findLevelRoomLeft(world, floor, wall, room2)) {
            for(int i = room1.hallwayPosition[2].atX; i > room1.hallwayPosition[2].atX - room1.hallwayPosition[2].len; i--) {
                world[i][room1.hallwayPosition[2].atY] = floor;
            }
            wrapHallway(world, wall, floor, room1.hallwayPosition[2]);

            room2.roomPosition.ConnectRightHallway = true;
            room1.roomPosition.ConnectLeftHallway = true;

        }
        if (room1.findLevelRoomRight(world, floor, wall, room2)) {

            for(int i = room1.hallwayPosition[3].atX; i < room1.hallwayPosition[3].atX + room1.hallwayPosition[3].len; i++) {
                world[i][room1.hallwayPosition[3].atY] = floor;
            }
            wrapHallway(world, wall, floor, room1.hallwayPosition[3]);

            room1.roomPosition.ConnectRightHallway = true;
            room2.roomPosition.ConnectLeftHallway = true;
        }
    }

    private static void wrapHallway(TETile[][] world, TETile tile, TETile floor, HallwayPosition hp) {
        if(hp.direction.equals("Vertical")) {
            if(hp.GoTo.equals("Up")) {
                for(int i = hp.atY; i < hp.len + hp.atY; i++) {
                    if(world[hp.atX - 1][i] != floor) {
                        world[hp.atX - 1][i] = tile;
                    }
                    if(world[hp.atX + 1][i] != floor) {
                        world[hp.atX + 1][i] = tile;
                    }
                }
            } else {
                for(int i = hp.atY; i > hp.atY - hp.len; i--) {
                    if(world[hp.atX - 1][i] != floor) {
                        world[hp.atX - 1][i] = tile;
                    }
                    if(world[hp.atX + 1][i] != floor) {
                        world[hp.atX + 1][i] = tile;
                    }
                }
            }
        } else if(hp.direction.equals("Level")) {
            if(hp.GoTo.equals("Right")) {
                for(int i = hp.atX; i < hp.len + hp.atX; i++) {
                    if(world[i][hp.atY - 1] != floor) {
                        world[i][hp.atY - 1] = tile;
                    }
                    if(world[i][hp.atY + 1] != floor) {
                        world[i][hp.atY + 1] = tile;
                    }
                }
            } else {
                for(int i = hp.atX; i > hp.atX - hp.len; i--) {
                    if(world[i][hp.atY - 1] != floor) {
                        world[i][hp.atY - 1] = tile;
                    }
                    if(world[i][hp.atY + 1] != floor) {
                        world[i][hp.atY + 1] = tile;
                    }
                }
            }
        }
    }

    public static void ConnectRoomHallway(TETile[][] world, TETile floor, TETile wall, Room room, Room[] rooms) {
        if(room.FindAHallwayUpwards(world, floor, wall, rooms)) {
            for(int i = room.hallwayPosition[0].atY; i < room.hallwayPosition[0].atY + room.hallwayPosition[0].len; i++) {
                world[room.hallwayPosition[0].atX][i] = floor;
            }
            wrapHallway(world, wall, floor, room.hallwayPosition[0]);
            room.roomPosition.ConnectUpwardHallway = true;
        }
        if(room.FindAHallwayDownwards(world, floor, wall, rooms)) {
            for(int i = room.hallwayPosition[1].atY; i > room.hallwayPosition[1].atY - room.hallwayPosition[1].len; i--) {
                world[room.hallwayPosition[1].atX][i] = floor;
            }
            wrapHallway(world, wall, floor, room.hallwayPosition[1]);
            room.roomPosition.ConnectDownwardHallway = true;
        }
        if(room.FindAHallwayLeft(world, floor, wall, rooms)) {
            for(int i = room.hallwayPosition[2].atX; i > room.hallwayPosition[2].atX - room.hallwayPosition[2].len; i--) {
                world[i][room.hallwayPosition[2].atY] = floor;
            }
            wrapHallway(world, wall, floor, room.hallwayPosition[2]);
            room.roomPosition.ConnectLeftHallway = true;
        }
        if(room.FindAHallwayRight(world, floor, wall, rooms)) {
            for(int i = room.hallwayPosition[3].atX; i < room.hallwayPosition[3].atX + room.hallwayPosition[3].len; i++) {
                world[i][room.hallwayPosition[3].atY] = floor;
            }
            wrapHallway(world, wall, floor, room.hallwayPosition[3]);
            room.roomPosition.ConnectRightHallway = true;
        }
    }

    public static void AddHallway(TETile[][] world, TETile floor, TETile wall, HallwayPosition hp) {
        if(hp.direction.equals("Vertical")) {
            if(hp.GoTo.equals("Up")) {
                for(int i = hp.atY; i < hp.len + hp.atY; i++) {
                    world[hp.atX][i] = floor;
                }
                wrapHallway(world, wall, floor, hp);
            } else {
                for(int i = hp.atY; i > hp.atY - hp.len; i--) {
                    world[hp.atX][i] = floor;
                }
                wrapHallway(world, wall, floor, hp);
            }
        } else if(hp.direction.equals("Level")) {
            if(hp.GoTo.equals("Right")) {
                for(int i = hp.atX; i < hp.len + hp.atX; i++) {
                    world[i][hp.atY] = floor;
                }
                wrapHallway(world, wall, floor, hp);
            } else {
                for(int i = hp.atX; i > hp.atX - hp.len; i--) {
                    world[i][hp.atY] = floor;
                }
                wrapHallway(world, wall, floor, hp);
            }
        }
    }
}
