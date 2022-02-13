package byog.Core;

import byog.TileEngine.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestRoom {
    @Test
    public void TestFindRoom() {
        GeneratingGame ggame = new GeneratingGame(48, 60, 50);
        //TERenderer show = new TERenderer();
        //show.initialize(ggame.world.length,ggame.world[0].length);
        int startX, startY, height, width;
        startX = 1;
        startY = 2;
        height = 5;
        width = 4;
        Room r = new Room();
        r.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 1;
        startY = 30;
        height = 1;
        width = 4;
        Room r1 = new Room();
        r1.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        Room rooms[] = new Room[2];
        rooms[0] = r;
        rooms[1] = r1;

        assertFalse(r.IsConnected(r1));

        assertTrue(r.findVerticalRoomUpwards(ggame.world, Tileset.FLOOR, Tileset.WALL, r1));
        assertTrue(r1.findVerticalRoomDownwards(ggame.world, Tileset.FLOOR, Tileset.WALL, r));

        Hallway.addVerticalHallway(ggame.world,Tileset.FLOOR, Tileset.WALL, r, r1);


        assertTrue(r.containsTheArea(1,3));
        //assertEquals(r1, r.FindConnectedUpwardRoom(rooms));
        //assertEquals(r, r1.FindConnectedDownRoom(rooms));
        //assertTrue(r.roomPosition.ConnectUpwardHallway);
        //assertTrue(r1.roomPosition.ConnectDownwardHallway);
        assertTrue(r.IsConnected(r1));
        assertTrue(r1.hallwayPosition[1] != null);
    }

    @Test
    public void TestLevelRoom() {
        GeneratingGame ggame = new GeneratingGame(48, 60, 50);
        //TERenderer show = new TERenderer();
        //show.initialize(ggame.world.length,ggame.world[0].length);
        int startX, startY, height, width;
        startX = 26;
        startY = 20;
        height = 4;
        width = 1;
        Room r = new Room();
        r.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 47;
        startY = 17;
        height = 5;
        width = 4;
        Room r1 = new Room();
        r1.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 34;
        startY = 17;
        height = 5;
        width = 4;
        Room r2 = new Room();
        r2.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        Room rooms[] = new Room[3];
        rooms[0] = r;
        rooms[1] = r1;
        rooms[2] = r2;

        assertFalse(r.findVerticalRoomUpwards(ggame.world, Tileset.FLOOR, Tileset.WALL, r1));
       // assertFalse(r1.findVerticalRoomDownwards(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms));

       // assertTrue(r1.findLevelRoomLeft(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms));
        //assertFalse(r1.findLevelRoomRight(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms));
       // assertFalse(r.findLevelRoomLeft(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms));
        //assertTrue(r1.findLevelRoomLeft(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms));

        //Hallway.addLevelHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, r, rooms);

        //assertTrue(r.roomPosition.ConnectRightHallway);
        //assertTrue(r1.roomPosition.ConnectLeftHallway);

        //assertEquals(r1, r.FindConnectedRightRoom(rooms));
        //assertEquals(r2, r1.FindConnectedLeftRoom(rooms));
        //assertTrue(r2.IsConnected(r1));
    }

    @Test
    public void TestRoom() {
        GeneratingGame ggame = new GeneratingGame(48, 60, 50);
        //TERenderer show = new TERenderer();
        //show.initialize(ggame.world.length,ggame.world[0].length);

        int startX, startY, height, width;
        startX = 47;
        startY = 17;
        height = 5;
        width = 4;
        Room r4 = new Room();
        r4.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 47;
        startY = 26;
        height = 3;
        width = 7;
        Room r5 = new Room();
        r5.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 47;
        startY = 35;
        height = 3;
        width = 7;
        Room r6 = new Room();
        r6.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        assertFalse(r4.findVerticalRoomUpwards(ggame.world, Tileset.FLOOR, Tileset.WALL, r6));
        assertTrue(r5.findVerticalRoomUpwards(ggame.world, Tileset.FLOOR, Tileset.WALL, r6));
        assertFalse(r6.findVerticalRoomDownwards(ggame.world, Tileset.FLOOR, Tileset.WALL, r4));
    }

    @Test
    public void TestHallway() {
        GeneratingGame ggame = new GeneratingGame(48, 60, 50);
        TERenderer show = new TERenderer();
        show.initialize(ggame.world.length,ggame.world[0].length);
        int startX, startY, height, width;
        startX = 1;
        startY = 2;
        height = 5;
        width = 4;
        Room r = new Room();
        r.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 1;
        startY = 30;
        height = 1;
        width = 4;
        Room r1 = new Room();
        r1.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 10;
        startY = 20;
        height = 6;
        width = 1;
        Room r3 = new Room();
        r3.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 25;
        startY = 36;
        height = 5;
        width = 4;
        Room r4 = new Room();
        r4.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 20;
        startY = 28;
        height = 3;
        width = 7;
        Room r5 = new Room();
        r5.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 23;
        startY = 17;
        height = 3;
        width = 7;
        Room r6 = new Room();
        r6.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 8;
        startY = 35;
        height = 6;
        width = 4;
        Room r7 = new Room();
        r7.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        Room rooms[] = new Room[7];
        rooms[0] = r4;
        rooms[1] = r5;
        rooms[2] = r6;
        rooms[3] = r;
        rooms[4] = r1;
        rooms[5] = r3;
        rooms[6] = r7;

        for(int i = 0; i < rooms.length; i++) {
            for(int j = i + 1; j < rooms.length; j++) {
                if(!rooms[i].IsConnected(rooms[j])) {
                    Hallway.addVerticalHallway(ggame.world,Tileset.FLOOR, Tileset.WALL, rooms[i], rooms[j]);
                    Hallway.addLevelHallway(ggame.world,Tileset.FLOOR, Tileset.WALL, rooms[i], rooms[j]);
                }
            }
        }

        assertTrue(r3.IsUnconnected());
        assertTrue(Room.IsNoRoomArea(rooms, r3.roomPosition.startX, r3.roomPosition.startY + r3.roomPosition.height));
        assertTrue(r3.FindAHallwayUpwards(ggame.world,Tileset.FLOOR, Tileset.WALL, rooms));
        assertTrue(!r7.IsUnconnected());
        assertTrue(Room.IsNoRoomArea(rooms, r7.roomPosition.startX - 2, r7.roomPosition.startY));
        assertTrue(r7.FindAHallwayDownwards(ggame.world,Tileset.FLOOR, Tileset.WALL, rooms));
        assertTrue(r6.FindAHallwayLeft(ggame.world,Tileset.FLOOR, Tileset.WALL, rooms));
        assertTrue(r3.FindAHallwayRight(ggame.world,Tileset.FLOOR, Tileset.WALL, rooms));
    }

    @Test
    public void TestIfOnlyConnectedOneAnotherVertical() {
        GeneratingGame ggame = new GeneratingGame(48, 60, 50);
        TERenderer show = new TERenderer();
        show.initialize(ggame.world.length,ggame.world[0].length);
        int startX, startY, height, width;

        startX = 1;
        startY = 30;
        height = 1;
        width = 4;
        Room r1 = new Room();
        r1.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 1;
        startY = 20;
        height = 6;
        width = 1;
        Room r3 = new Room();
        r3.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        Room[] rooms = new Room[2];
        Hallway.addVerticalHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, r1, r3);

        assertTrue(Room.IfOnlyConnectedVerticalOneAnother(r1, r3));
    }

    @Test
    public void TestIfOnlyConnectedOneAnotherLevel() {
        GeneratingGame ggame = new GeneratingGame(48, 60, 50);
        TERenderer show = new TERenderer();
        show.initialize(ggame.world.length,ggame.world[0].length);
        int startX, startY, height, width;

        startX = 25;
        startY = 20;
        height = 1;
        width = 4;
        Room r1 = new Room();
        r1.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 1;
        startY = 20;
        height = 6;
        width = 1;
        Room r3 = new Room();
        r3.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        Room[] rooms = new Room[2];
        Hallway.addLevelHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, r1, r3);

        assertTrue(Room.IfOnlyConnectedLevelOneAnother(r1, r3));
    }

    public static void main(String[] args) {
        GeneratingGame ggame = new GeneratingGame(30, 80, 50);
        TERenderer show = new TERenderer();
        show.initialize(ggame.world.length,ggame.world[0].length);
        int startX, startY, height, width;
        /*startX = 1;
        startY = 2;
        height = 5;
        width = 4;
        Room r = new Room();
        r.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 1;
        startY = 30;
        height = 1;
        width = 4;
        Room r1 = new Room();
        r1.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 10;
        startY = 21;
        height = 6;
        width = 1;
        Room r3 = new Room();
        r3.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 25;
        startY = 36;
        height = 5;
        width = 4;
        Room r4 = new Room();
        r4.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 20;
        startY = 28;
        height = 3;
        width = 7;
        Room r5 = new Room();
        r5.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 23;
        startY = 17;
        height = 3;
        width = 7;
        Room r6 = new Room();
        r6.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        startX = 8;
        startY = 35;
        height = 6;
        width = 4;
        Room r7 = new Room();
        r7.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        Room rooms[] = new Room[7];
        rooms[0] = r4;
        rooms[1] = r5;
        rooms[2] = r6;
        rooms[3] = r;
        rooms[4] = r1;
        rooms[5] = r3;
        rooms[6] = r7;

        /*Room rooms1[] = new Room[4];
        rooms1[0] = r;
        rooms1[1] = r1;
        rooms1[2] = r3;
        rooms1[3] = r4;*/
        /*
        for(int i = 0; i < rooms.length; i++) {
            for(int j = 0; j < rooms.length; j++) {
                if(!rooms[i].IsConnected(rooms[j])) {
                    Hallway.addVerticalHallway(ggame.world,Tileset.FLOOR, Tileset.WALL, rooms[i], rooms[j]);
                    Hallway.addLevelHallway(ggame.world,Tileset.FLOOR, Tileset.WALL, rooms[i], rooms[j]);
                }

            }
            //Hallway.addLevelHallway(ggame.world, Tileset.FLOOR, Tileset.WALL, rooms[i], rooms);
        }
        for(int i = 0; i < rooms.length; i++) {
            if(rooms[i].IsUnconnected()) {
                Hallway.ConnectRoomHallway(ggame.world,Tileset.FLOOR, Tileset.WALL, rooms[i], rooms);
            }
        }
        Hallway.ConnectRoomHallway(ggame.world,Tileset.FLOOR, Tileset.WALL, r6, rooms);
        Hallway.ConnectRoomHallway(ggame.world,Tileset.FLOOR, Tileset.WALL, r7, rooms);*/
        Room[] rooms = new Room[2];
        Room r1 = new Room();
        startX = 74;
        startY = 25;
        width = 3;
        height = 4;
        r1.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        Room r2 = new Room();
        startX = 2;
        startY = 2;
        width = 4;
        height = 6;
        r2.addRoom(ggame.world, startX, startY, Tileset.FLOOR, Tileset.WALL, height, width);

        r2.FindWayToAnotherRoom(ggame.world, Tileset.FLOOR, Tileset.WALL, r1);

        show.renderFrame(ggame.world);
    }
}
