package byog.Core;

import byog.TileEngine.*;
import java.util.Random;
import static byog.Core.RandomUtils.*;

public class Room {
    private static Random random = new Random(GeneratingGame.seed);
    public RoomPosition roomPosition;
    public HallwayPosition hallwayPosition[] = new HallwayPosition[4];
    int[] Widthbook;
    int[] Heightbook;

    public void addRoom(TETile[][] world, int startX, int startY, TETile floor, TETile wall, int height, int width) {
        for(int i = startY; i < height + startY && i < world[0].length; i++) {
            for(int j = startX; j < width + startX && j < world.length; j++) {
                world[j][i] = floor;
            }
        }
        roomPosition = new RoomPosition(startX, startY, width, height);
        Widthbook = new int[width];
        Heightbook = new int[height];
        WrapWalls(world, wall, startX, startY, height, width);
    }

    private static void WrapWalls(TETile[][] world, TETile wall, int startX, int startY, int height, int width) {
        //fill the Room-around with walls
        //The bottom row
        for(int i = startX - 1; i < width + 1 + startX && i < world.length; i++) {
            if(i < 0) {
                continue;
            }
            if(startY > 0) {
                world[i][startY - 1] = wall;
            } else {
                world[i][0] = wall;
            }
        }
        //The left line
        for(int i = startY - 1; i < height + 1 + startY && i < world[0].length; i++) {
            if(i < 0) {
                continue;
            }
            if(startX > 0) {
                world[startX - 1][i] = wall;
            } else {
                world[0][i] = wall;
            }
        }
        //The right line
        for(int i = startY - 1; i < height + 1 + startY && i < world[0].length; i++) {
            if(i < 0) {
                continue;
            }
            if(startX + width >= world.length) {
                world[world.length - 1][i] = wall;
            } else {
                world[startX + width][i] = wall;
            }
        }
        //The top line
        for(int i = startX - 1; i < width + 1 + startX && i < world.length; i++) {
            if(i < 0) {
                continue;
            }
            if(startY + height >= world[0].length) {
                world[i][world[0].length - 1] = wall;
            } else {
                world[i][startY + height] = wall;
            }
        }
    }

    public static int getWidth() {
        return RandomUtils.uniform(random, 2, 8);
    }

    public static int getHeight() {
        return RandomUtils.uniform(random, 2, 8);
    }

    public static boolean IsThereARoom(TETile[][] world, TETile tile, int startX, int startY, int height, int width) {
        if(startX >= 2 && startY >= 2) {
            for(int i = startY; i < startY + height + 3 && i < world[0].length; i++) {
                for(int j = startX; j < startX + width + 3 && j < world.length; j++) {
                    if(world[j - 2][i - 2].equals(tile) || world[j - 2][i - 2].equals(Tileset.WALL)) {
                        return true;
                    }
                }
            }
        } else if(startX >= 2 && startY < 2) {
            for(int i = startY; i < startY + height + 3 && i < world[0].length; i++) {
                for(int j = startX; j < startX + width + 3 && j < world.length; j++) {
                    if(world[j - 2][i].equals(tile) || world[j - 2][i].equals(Tileset.WALL)) {
                        return true;
                    }
                }
            }
        } else if(startX < 2 && startY > 2) {
            for(int i = startY; i < startY + height + 3 && i < world[0].length; i++) {
                for(int j = startX; j < startX + width + 3 && j < world.length; j++) {
                    if(world[j][i - 2].equals(tile) || world[j][i - 2].equals(Tileset.WALL)) {
                        return true;
                    }
                }
            }
        } else {
            for(int i = startY; i < startY + height + 3 && i < world[0].length; i++) {
                for(int j = startX; j < startX + width + 3 && j < world.length; j++) {
                    if(world[j][i].equals(tile) || world[j][i].equals(Tileset.WALL)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //向上查找是否可以相连，出口任意
    public boolean findVerticalRoomUpwards(TETile[][] world, TETile floor, TETile wall, Room room) {
        if(room.roomPosition.startX + room.roomPosition.width - 1 < this.roomPosition.startX || room.roomPosition.startX > this.roomPosition.startX + this.roomPosition.width - 1) {
            return false;
        }
        /*if (this.roomPosition.ConnectUpwardHallway) {
            return false;
        }*/
        //Test: System.out.println("New:");
        int randomnum = 0;
        int[] book = new int[this.roomPosition.width];
        for(int i = roomPosition.startX; i < roomPosition.width + roomPosition.startX; i++) {
            /*while(randomnum == 0) {
                randomnum = RandomUtils.uniform(random, roomPosition.startX, roomPosition.width + roomPosition.startX);
            }
            Actually, the code above only generate one random number.*/
            randomnum = RandomUtils.uniform(random, this.roomPosition.startX, this.roomPosition.startX + this.roomPosition.width);
            while(book[randomnum - this.roomPosition.startX] != 0) {
                randomnum = RandomUtils.uniform(random, this.roomPosition.startX, this.roomPosition.startX + this.roomPosition.width);
            }
            book[randomnum - this.roomPosition.startX] = 1;
            if (randomnum <= 0 || randomnum >= world.length - 1 || Widthbook[randomnum - this.roomPosition.startX] == 1) {
                continue;
            }
            //Test:  System.out.print(randomnum + " ");
            if(findVerticalRoomUpwards(world, randomnum, roomPosition.startY + roomPosition.height, floor, wall, room)) {
                //Test: System.out.println();
                return true;
            }
        }
        return false;
    }

    private boolean findVerticalRoomUpwards(TETile[][] world, int startX, int startY, TETile floor, TETile wall, Room room) {
        if(startX >= world.length) {
            return false;
        }
        for(int i = startY; i < world[0].length - 1; i++) {
            if(room.containsTheArea(startX, i)) {
                //为自己设置走廊位置
                hallwayPosition[0] = new HallwayPosition(startX, startY, "Vertical", i - startY, "Up");
                //相应地为连接的房间设置
                room.hallwayPosition[1] = new HallwayPosition(startX, this.hallwayPosition[0].atY + this.hallwayPosition[0].len - 1, "Vertical", this.hallwayPosition[0].len, "Down");
                this.Widthbook[startX - this.roomPosition.startX] = 1;
                return true;
            }
            if(world[startX][i] == wall && world[startX][i + 1] == floor && !room.containsTheArea(startX, i + 1)) {
                return false;
            }
        }
        return false;
    }

    //向下查找是否可以相连，出口任意
    public boolean findVerticalRoomDownwards(TETile[][] world, TETile floor, TETile wall, Room room) {
        if(room.roomPosition.startX + room.roomPosition.width - 1 < this.roomPosition.startX || room.roomPosition.startX > this.roomPosition.startX + this.roomPosition.width - 1) {
            return false;
        }
        /*if (this.roomPosition.ConnectDownwardHallway) {
            return false;
        }*/
        int randomnum = 0;
        int[] book = new int[this.roomPosition.width];
        //Test: System.out.println("New:");
        for(int i = roomPosition.startX; i < roomPosition.width + roomPosition.startX; i++) {
            /*while(randomnum == 0) {
                randomnum = RandomUtils.uniform(random, roomPosition.startX, roomPosition.width + roomPosition.startX);
            }
            Actually, the code above only generate one random number.*/
            randomnum = RandomUtils.uniform(random, this.roomPosition.startX, this.roomPosition.startX + this.roomPosition.width);
            while(book[randomnum - this.roomPosition.startX] != 0) {
                randomnum = RandomUtils.uniform(random, this.roomPosition.startX, this.roomPosition.startX + this.roomPosition.width);
            }
            book[randomnum - this.roomPosition.startX] = 1;
            if (randomnum <= 0 || randomnum >= world.length - 1 || Widthbook[randomnum - this.roomPosition.startX] == 1) {
                continue;
            }
            //Test: System.out.print(randomnum + " ");
            if(findVerticalRoomDownwards(world, randomnum, roomPosition.startY - 1, floor, wall, room)) {
                //Test: System.out.println();
                return true;
            }
        }
        return false;
    }

    private boolean findVerticalRoomDownwards(TETile[][] world, int startX, int startY, TETile floor, TETile wall, Room room) {
        if(startX >= world.length) {
            return false;
        }
        for(int i = startY; i > 0; i--) {
            if(room.containsTheArea(startX, i)) {
                hallwayPosition[1] = new HallwayPosition(startX, startY, "Vertical", startY - i, "Down");
                room.hallwayPosition[0] = new HallwayPosition(startX, this.hallwayPosition[1].atY - this.hallwayPosition[1].len + 1, "Vertical", this.hallwayPosition[1].len, "Up");
                Widthbook[startX - this.roomPosition.startX] = 1;
                return true;
            }
            if(world[startX][i] == wall && world[startX][i - 1] == floor && !room.containsTheArea(startX, i - 1)) {
                return false;
            }
        }
        return false;
    }

    public boolean findLevelRoomLeft(TETile[][] world, TETile floor, TETile wall, Room room) {
        if(room.roomPosition.startY >= this.roomPosition.startY + this.roomPosition.height || room.roomPosition.startY + room.roomPosition.height <= this.roomPosition.startY) {
            return false;
        }
        /*if(this.roomPosition.ConnectLeftHallway) {
            return false;
        }*/
        int randomnum = 0;
        int[] book = new int[this.roomPosition.height];
        for(int i = 0; i < this.roomPosition.height; i++) {
            randomnum = RandomUtils.uniform(random, this.roomPosition.startY, this.roomPosition.startY + this.roomPosition.height);
            while(book[randomnum - this.roomPosition.startY] != 0) {
                randomnum = RandomUtils.uniform(random, this.roomPosition.startY, this.roomPosition.startY + this.roomPosition.height);
            }
            book[randomnum - this.roomPosition.startY] = 1;
            if (randomnum <= 0 || randomnum >= world[0].length - 1 || Heightbook[randomnum - this.roomPosition.startY] == 1) {
                continue;
            }
            if(findLevelRoomLeft(world, wall, floor, this.roomPosition.startX - 1, randomnum, room)) {
                return true;
            }
        }
        return false;
    }

    private boolean findLevelRoomLeft(TETile[][] world, TETile wall, TETile floor, int startX, int startY, Room room) {
        if (startY >= world[0].length) {
            return false;
        }
        for(int i = startX; i > 0; i--) {
            if (room.containsTheArea(i, startY)) {
                this.hallwayPosition[2] = new HallwayPosition(startX, startY, "Level", startX - i, "Left");
                room.hallwayPosition[3] = new HallwayPosition(this.hallwayPosition[2].atX - this.hallwayPosition[2].len + 1, startY, "Level", this.hallwayPosition[2].len, "Right");
                Heightbook[startY - this.roomPosition.startY] = 1;
                return true;
            }
            if(world[i][startY] == wall && world[i - 1][startY] == floor && !room.containsTheArea(i - 1, startY)) {
                return false;
            }
        }
        return false;
    }

    public boolean findLevelRoomRight(TETile[][] world, TETile floor, TETile wall, Room room) {
        if(room.roomPosition.startY >= this.roomPosition.startY + this.roomPosition.height || room.roomPosition.startY + room.roomPosition.height <= this.roomPosition.startY) {
            return false;
        }
        /*if(this.roomPosition.ConnectRightHallway) {
            return false;
        }*/
        int randomnum = 0;
        int[] book = new int[this.roomPosition.height];
        for(int i = 0; i < this.roomPosition.height; i++) {
            randomnum = RandomUtils.uniform(random, this.roomPosition.startY, this.roomPosition.startY + this.roomPosition.height);
            while(book[randomnum - this.roomPosition.startY] != 0) {
                randomnum = RandomUtils.uniform(random, this.roomPosition.startY, this.roomPosition.startY + this.roomPosition.height);
            }
            book[randomnum - this.roomPosition.startY] = 1;
            if(randomnum == 0 || randomnum == world[0].length - 1 || Heightbook[randomnum - this.roomPosition.startY] == 1) {
                continue;
            }
            if(findLevelRoomRight(world, wall, floor, this.roomPosition.startX + this.roomPosition.width, randomnum, room)) {
                return true;
            }
        }
        return false;
    }

    private boolean findLevelRoomRight(TETile[][] world, TETile wall, TETile floor, int startX, int startY, Room room) {
        if (startY >= world[0].length) {
            return false;
        }
        for(int i = startX; i < world.length - 1; i++) {
            if (room.containsTheArea(i, startY)) {
                this.hallwayPosition[3] = new HallwayPosition(startX, startY, "Level", i - startX, "Right");
                room.hallwayPosition[2] = new HallwayPosition(this.hallwayPosition[3].atX + this.hallwayPosition[3].len - 1, this.hallwayPosition[3].atY, "Level", this.hallwayPosition[3].len, "Left");
                this.Heightbook[startY - this.roomPosition.startY] = 1;
                return true;
            }
            if(world[i][startY] == wall && world[i + 1][startY] == floor && !room.containsTheArea(i + 1, startY)) {
                return false;
            }
        }
        return false;
    }

    /*
    Unusable code
    public Room FindConnectedUpwardRoom(Room[] rooms) {
        int i = 0;
        if(this.hallwayPosition[0] != null) {
            for(i = 0; i < rooms.length; i++) {
                if(rooms[i].containsTheArea(this.hallwayPosition[0].atX, this.hallwayPosition[0].atY + this.hallwayPosition[0].len)) {
                    return rooms[i];
                }
            }
        }
        return null;
    }

    public Room FindConnectedDownRoom(Room[] rooms) {
        int i = 0;
        if(this.hallwayPosition[1] != null) {
            for(i = 0; i < rooms.length; i++) {
                if(rooms[i].containsTheArea(this.hallwayPosition[1].atX, this.hallwayPosition[1].atY - this.hallwayPosition[1].len)) {
                    return rooms[i];
                }
            }
        }
        return null;
    }

    public Room FindConnectedLeftRoom(Room[] rooms) {
        int i = 0;
        if (this.hallwayPosition[2] != null) {
            for(i = 0; i < rooms.length; i++) {
                if(rooms[i].containsTheArea(this.hallwayPosition[2].atX - this.hallwayPosition[2].len, this.hallwayPosition[2].atY)) {
                    return rooms[i];
                }
            }
        }
        return null;
    }

    public Room FindConnectedRightRoom(Room[] rooms) {
        int i = 0;
        if (this.hallwayPosition[3] != null) {
            for(i = 0; i < rooms.length; i++) {
                if(rooms[i].containsTheArea(this.hallwayPosition[3].atX + this.hallwayPosition[3].len, this.hallwayPosition[3].atY)) {
                    return rooms[i];
                }
            }
        }
        return null;
    }
    */

    public boolean containsTheArea(int pointX, int pointY) {
        for(int i = this.roomPosition.startY; i < this.roomPosition.startY + this.roomPosition.height; i++) {
            for(int j = this.roomPosition.startX; j < this.roomPosition.width + this.roomPosition.startX; j++) {
                if(i == pointY && j == pointX) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean IsConnected(Room r1) {
        for(int i = 0; i < this.hallwayPosition.length; i++) {
            switch(i) {
                case 0:
                    if(this.hallwayPosition[0] != null && r1.hallwayPosition[1] != null) {
                        if(HallwayPosition.IsTheSameVerticalHallway(this.hallwayPosition[0], r1.hallwayPosition[1])) {
                            return true;
                        }
                    }
                    break;
                case 1:
                    if(this.hallwayPosition[1] != null && r1.hallwayPosition[0] != null) {
                        if(HallwayPosition.IsTheSameVerticalHallway(this.hallwayPosition[1], r1.hallwayPosition[0])) {
                            return true;
                        }
                    }
                    break;
                case 2:
                    if(this.hallwayPosition[2] != null && r1.hallwayPosition[3] != null) {
                        if(HallwayPosition.IsTheSameLevelHallway(this.hallwayPosition[2], r1.hallwayPosition[3])) {
                            return true;
                        }
                    }
                    break;
                case 3:
                    if(this.hallwayPosition[3] != null && r1.hallwayPosition[2] != null) {
                        if(HallwayPosition.IsTheSameLevelHallway(this.hallwayPosition[3], r1.hallwayPosition[2])) {
                            return true;
                        }
                    }
                    break;
            }
        }
        return false;
    }

    public boolean IsUnconnected() {
        if(!this.roomPosition.ConnectUpwardHallway && !this.roomPosition.ConnectDownwardHallway
        && !this.roomPosition.ConnectRightHallway && !this.roomPosition.ConnectLeftHallway) {
            return true;
        }
        return false;
    }

    public static boolean IsNoRoomArea(Room[] rooms, int pointX, int pointY) {
        int flag = 0;
        for(int i = 0;i < rooms.length; i++) {
            if(rooms[i].containsTheArea(pointX, pointY)) {
                flag = 1;
                break;
            }
        }
        return (flag == 0);
    }

    public boolean FindAHallwayUpwards(TETile[][] world, TETile floor, TETile wall, Room[] rooms) {
        //Upwards
        int randomnum = 0;
        int[] book = new int[this.roomPosition.width];
        for(int i = this.roomPosition.startX; i < this.roomPosition.startX + this.roomPosition.width; i++) {
            randomnum = RandomUtils.uniform(random, this.roomPosition.startX, this.roomPosition.startX + this.roomPosition.width);
            while(book[randomnum - this.roomPosition.startX] != 0) {
                randomnum = RandomUtils.uniform(random, this.roomPosition.startX, this.roomPosition.startX + this.roomPosition.width);
            }
            book[randomnum - this.roomPosition.startX] = 1;
            if(randomnum <= 0 || randomnum >= world.length || Widthbook[randomnum - this.roomPosition.startX] == 1) {
                continue;
            }
            if(FindAHallwayUpwards(world, floor, wall, randomnum, this.roomPosition.startY + this.roomPosition.height, rooms)) {
                return true;
            }
        }
        return false;
    }

    private boolean FindAHallwayUpwards(TETile[][] world, TETile floor, TETile wall, int startX, int startY, Room[] rooms) {
        if(startX >= world.length) {
            return false;
        }
        for(int i = startY; i < world[0].length - 1; i++) {
            if(world[startX][i] == wall && world[startX][i + 1] == floor && IsNoRoomArea(rooms, startX, i + 1)) {
                this.hallwayPosition[0] = new HallwayPosition(startX, startY, "Vertical", i + 1 - startY, "Up");
                Widthbook[startX - this.roomPosition.startX] = 1;
                return true;
            }
        }
        return false;
    }

    public boolean FindAHallwayDownwards(TETile[][] world, TETile floor, TETile wall, Room[] rooms) {
        //Upwards
        int randomnum = 0;
        int[] book = new int[this.roomPosition.width];
        for(int i = this.roomPosition.startX; i < this.roomPosition.startX + this.roomPosition.width; i++) {
            randomnum = RandomUtils.uniform(random, this.roomPosition.startX, this.roomPosition.startX + this.roomPosition.width);
            while(book[randomnum - this.roomPosition.startX] != 0) {
                randomnum = RandomUtils.uniform(random, this.roomPosition.startX, this.roomPosition.startX + this.roomPosition.width);
            }
            book[randomnum - this.roomPosition.startX] = 1;
            if(randomnum <= 0 || randomnum >= world.length || Widthbook[randomnum - this.roomPosition.startX] == 1) {
                continue;
            }
            if(FindAHallwayDownwards(world, floor, wall, randomnum, this.roomPosition.startY - 1, rooms)) {
                return true;
            }
        }
        return false;
    }

    private boolean FindAHallwayDownwards(TETile[][] world, TETile floor, TETile wall, int startX, int startY, Room[] rooms) {
        if(startX >= world.length) {
            return false;
        }
        for(int i = startY; i > 0; i--) {
            if(world[startX][i] == wall && world[startX][i - 1] == floor && IsNoRoomArea(rooms, startX, i - 1)) {
                this.hallwayPosition[1] = new HallwayPosition(startX, startY, "Vertical", startY - i + 1, "Down");
                Widthbook[startX - this.roomPosition.startX] = 1;
                return true;
            }
        }
        return false;
    }

    public boolean FindAHallwayLeft(TETile[][] world, TETile floor, TETile wall, Room[] rooms) {
        //Upwards
        int randomnum = 0;
        int[] book = new int[this.roomPosition.height];
        for(int i = this.roomPosition.startY; i < this.roomPosition.startY + this.roomPosition.height; i++) {
            randomnum = RandomUtils.uniform(random, this.roomPosition.startY, this.roomPosition.startY + this.roomPosition.height);
            while(book[randomnum - this.roomPosition.startY] != 0) {
                randomnum = RandomUtils.uniform(random, this.roomPosition.startY, this.roomPosition.startY + this.roomPosition.height);
            }
            book[randomnum - this.roomPosition.startY] = 1;
            if(randomnum <= 0 || randomnum >= world[0].length || Heightbook[randomnum - this.roomPosition.startY] == 1) {
                continue;
            }
            if(FindAHallwayLeft(world, floor, wall, this.roomPosition.startX - 1, randomnum, rooms)) {
                return true;
            }
        }
        return false;
    }

    private boolean FindAHallwayLeft(TETile[][] world, TETile floor, TETile wall, int startX, int startY, Room[] rooms) {
        if (startY >= world[0].length) {
            return false;
        }
        for(int i = startX; i > 0; i--) {
            if(world[i][startY] == wall && world[i - 1][startY] == floor && IsNoRoomArea(rooms, i - 1, startY)) {
                this.hallwayPosition[2] = new HallwayPosition(startX, startY, "Level", startX - i + 1, "Left");
                Heightbook[startY - this.roomPosition.startY] = 1;
                return true;
            }
        }
        return false;
    }

    public boolean FindAHallwayRight(TETile[][] world, TETile floor, TETile wall, Room[] rooms) {
        //Upwards
        int randomnum = 0;
        int[] book = new int[this.roomPosition.height];
        for(int i = this.roomPosition.startY; i < this.roomPosition.startY + this.roomPosition.height; i++) {
            randomnum = RandomUtils.uniform(random, this.roomPosition.startY, this.roomPosition.startY + this.roomPosition.height);
            while(book[randomnum - this.roomPosition.startY] != 0) {
                randomnum = RandomUtils.uniform(random, this.roomPosition.startY, this.roomPosition.startY + this.roomPosition.height);
            }
            book[randomnum - this.roomPosition.startY] = 1;
            if(randomnum <= 0 || randomnum >= world[0].length || Heightbook[randomnum - this.roomPosition.startY] == 1) {
                continue;
            }
            if(FindAHallwayRight(world, floor, wall, this.roomPosition.startX + this.roomPosition.width, randomnum, rooms)) {
                return true;
            }
        }
        return false;
    }

    private boolean FindAHallwayRight(TETile[][] world, TETile floor, TETile wall, int startX, int startY, Room[] rooms) {
        if (startY >= world[0].length) {
            return false;
        }
        for(int i = startX; i < world.length - 1; i++) {
            if(world[i][startY] == wall && world[i + 1][startY] == floor && IsNoRoomArea(rooms, i + 1, startY)) {
                this.hallwayPosition[3] = new HallwayPosition(startX, startY, "Level", i - startX + 1, "Right");
                Heightbook[startY - this.roomPosition.startY] = 1;
                return true;
            }
        }
        return false;
    }

    //检查是否只是互相竖直连通，未与其他的地方相连
    public static boolean IfOnlyConnectedVerticalOneAnother(Room r1, Room r2) {
        //r1 -- upwards , r2 -- downwards
        if(r1.hallwayPosition[0] != null && r2.hallwayPosition[1] != null) {
            if(HallwayPosition.IsTheSameVerticalHallway(r1.hallwayPosition[0], r2.hallwayPosition[1])) {
                if(!r1.roomPosition.ConnectRightHallway && !r1.roomPosition.ConnectLeftHallway && !r1.roomPosition.ConnectDownwardHallway
                        && !r2.roomPosition.ConnectRightHallway && !r2.roomPosition.ConnectLeftHallway && !r2.roomPosition.ConnectUpwardHallway) {
                    return true;
                }
            }
        }
        if(r1.hallwayPosition[1] != null && r2.hallwayPosition[0] != null) {
            if (HallwayPosition.IsTheSameVerticalHallway(r1.hallwayPosition[1], r2.hallwayPosition[0])) {
                if(!r1.roomPosition.ConnectRightHallway && !r1.roomPosition.ConnectLeftHallway && !r1.roomPosition.ConnectUpwardHallway
                        && !r2.roomPosition.ConnectRightHallway && !r2.roomPosition.ConnectLeftHallway && !r2.roomPosition.ConnectDownwardHallway) {
                    return true;
                }
            }
        }
        return false;
    }

    //检查是否只是互相水平连通，未与其他的地方相连
    public static boolean IfOnlyConnectedLevelOneAnother(Room r1, Room r2) {
        //r1 -- upwards , r2 -- downwards
        if(r1.hallwayPosition[2] != null && r2.hallwayPosition[3] != null) {
            if(HallwayPosition.IsTheSameLevelHallway(r1.hallwayPosition[2], r2.hallwayPosition[3])) {
                if(!r1.roomPosition.ConnectRightHallway && !r1.roomPosition.ConnectUpwardHallway && !r1.roomPosition.ConnectDownwardHallway
                        && !r2.roomPosition.ConnectLeftHallway && !r2.roomPosition.ConnectDownwardHallway && !r2.roomPosition.ConnectUpwardHallway) {
                    return true;
                }
            }
        }
        if(r1.hallwayPosition[3] != null && r2.hallwayPosition[2] != null) {
            if (HallwayPosition.IsTheSameLevelHallway(r1.hallwayPosition[3], r2.hallwayPosition[2])) {
                if(!r1.roomPosition.ConnectLeftHallway && !r1.roomPosition.ConnectDownwardHallway && !r1.roomPosition.ConnectUpwardHallway
                        && !r2.roomPosition.ConnectRightHallway && !r2.roomPosition.ConnectUpwardHallway && !r2.roomPosition.ConnectDownwardHallway) {
                    return true;
                }
            }
        }
        return false;
    }

    public void FindWayToAnotherRoom(TETile[][] world, TETile floor, TETile wall, Room room) {
        Room temp = new Room();
        temp.roomPosition = new RoomPosition(this.roomPosition.startX, this.roomPosition.startY, this.roomPosition.width, this.roomPosition.height);
        temp.Widthbook = new int[this.roomPosition.width];
        temp.Heightbook = new int[this.roomPosition.height];
        //Right
        for(int i = this.roomPosition.startX + this.roomPosition.width; i < world.length - 1; i++) {
            temp.roomPosition.startX = i;
            if(temp.findVerticalRoomDownwards(world, floor, wall, room)) {
                this.hallwayPosition[3] = new HallwayPosition(this.roomPosition.startX + this.roomPosition.width - 1, temp.hallwayPosition[1].atY, "Level", temp.hallwayPosition[1].atX - this.roomPosition.startX - this.roomPosition.width + 2, "Right");
                temp.hallwayPosition[2] = new HallwayPosition(this.roomPosition.startX + this.roomPosition.width + this.hallwayPosition[3].len - 1, this.hallwayPosition[3].atY, "Level", this.hallwayPosition[3].len, "Left");
                temp.hallwayPosition[1].len++;
                Hallway.AddHallway(world, Tileset.FLOOR, Tileset.WALL, this.hallwayPosition[3]);
                Hallway.AddHallway(world, Tileset.FLOOR, Tileset.WALL, temp.hallwayPosition[1]);
                world[temp.hallwayPosition[1].atX + 1][temp.hallwayPosition[1].atY + 1] = wall;
                break;
            }
            if(temp.findVerticalRoomUpwards(world, floor, wall, room)) {
                this.hallwayPosition[3] = new HallwayPosition(this.roomPosition.startX + this.roomPosition.width - 1, temp.hallwayPosition[0].atY, "Level", temp.hallwayPosition[0].atX - this.roomPosition.startX - this.roomPosition.width + 2, "Right");
                temp.hallwayPosition[2] = new HallwayPosition(this.roomPosition.startX + this.roomPosition.width + this.hallwayPosition[3].len - 1, this.hallwayPosition[3].atY, "Level", this.hallwayPosition[3].len, "Left");
                temp.hallwayPosition[0].len++;
                Hallway.AddHallway(world, Tileset.FLOOR, Tileset.WALL, this.hallwayPosition[3]);
                Hallway.AddHallway(world, Tileset.FLOOR, Tileset.WALL, temp.hallwayPosition[0]);
                world[temp.hallwayPosition[0].atX + 1][temp.hallwayPosition[0].atY - 1] = wall;
                break;
            }
        }
        //Left
        for(int i = this.roomPosition.startX - 1; i > 1; i--) {
            temp.roomPosition.startX = i;
            if(temp.findVerticalRoomUpwards(world, floor, wall, room)) {
                if(temp.hallwayPosition[0].atY > 1) {
                    this.hallwayPosition[2] = new HallwayPosition(this.roomPosition.startX - 1, temp.hallwayPosition[0].atY + 1, "Level", this.roomPosition.startX - i, "Left");
                    temp.hallwayPosition[0].atY--;
                    temp.hallwayPosition[0].len++;
                }
                temp.hallwayPosition[3] = new HallwayPosition(this.hallwayPosition[2].atX - temp.hallwayPosition[0].len + 1, this.hallwayPosition[2].atY, "Level", this.hallwayPosition[2].len, "Right");
                Hallway.AddHallway(world, Tileset.FLOOR, Tileset.WALL, this.hallwayPosition[2]);
                Hallway.AddHallway(world, Tileset.FLOOR, Tileset.WALL, temp.hallwayPosition[0]);
                world[temp.hallwayPosition[0].atX - 1][temp.hallwayPosition[0].atY - 1] = wall;
                break;
            }
            if(temp.findVerticalRoomDownwards(world, floor, wall, room)) {
                if(temp.hallwayPosition[1].atY < world[0].length - 2) {
                    this.hallwayPosition[2] = new HallwayPosition(this.roomPosition.startX - 1, temp.hallwayPosition[1].atY + 1, "Level", this.roomPosition.startX - i, "Left");
                    temp.hallwayPosition[1].atY++;
                    temp.hallwayPosition[1].len++;
                } else {
                    this.hallwayPosition[2] = new HallwayPosition(this.roomPosition.startX - 1, temp.hallwayPosition[1].atY, "Level", this.roomPosition.startX - i, "Left");
                }
                temp.hallwayPosition[3] = new HallwayPosition(this.hallwayPosition[2].atX - temp.hallwayPosition[1].len + 1, this.hallwayPosition[2].atY, "Level", this.hallwayPosition[2].len, "Right");
                Hallway.AddHallway(world, Tileset.FLOOR, Tileset.WALL, this.hallwayPosition[2]);
                Hallway.AddHallway(world, Tileset.FLOOR, Tileset.WALL, temp.hallwayPosition[1]);
                world[temp.hallwayPosition[1].atX - 1][temp.hallwayPosition[1].atY + 1] = wall;
                break;
            }
        }
        temp.roomPosition.startX = this.roomPosition.startX;
        //Down
        for(int i = this.roomPosition.startY - 1; i > 1; i--) {
            temp.roomPosition.startY = i;
            if(temp.findLevelRoomLeft(world, floor, wall, room)) {
                this.hallwayPosition[1] = new HallwayPosition(temp.hallwayPosition[2].atX, this.roomPosition.startY - 1, "Vertical", this.roomPosition.startY - i, "Down");
                temp.hallwayPosition[0] = new HallwayPosition(this.hallwayPosition[1].atX, this.hallwayPosition[1].atY - this.hallwayPosition[1].len + 1, "Vertical", this.hallwayPosition[1].len, "Up");
                Hallway.AddHallway(world, floor, wall, this.hallwayPosition[1]);
                Hallway.AddHallway(world, floor, wall, temp.hallwayPosition[2]);
                world[temp.hallwayPosition[2].atX + 1][temp.hallwayPosition[2].atY - 1] = wall;
                break;
            }
            if(temp.findLevelRoomRight(world, floor, wall, room)) {
                this.hallwayPosition[1] = new HallwayPosition(this.hallwayPosition[3].atX, this.roomPosition.startY - 1, "Vertical", this.roomPosition.startY - i, "Down");
                temp.hallwayPosition[0] = new HallwayPosition(this.hallwayPosition[1].atX, this.hallwayPosition[1].atY - this.hallwayPosition[1].len + 1, "Vertical", this.hallwayPosition[1].len, "Up");
                Hallway.AddHallway(world, floor, wall, this.hallwayPosition[1]);
                Hallway.AddHallway(world, floor, wall, temp.hallwayPosition[3]);
                world[temp.hallwayPosition[3].atX - 1][temp.hallwayPosition[3].atY - 1] = wall;
                break;
            }
        }
        //Up
        for(int i = this.roomPosition.startY + this.roomPosition.height; i < world[0].length - 1; i++) {
            temp.roomPosition.startY = i;
            if(temp.findLevelRoomLeft(world, floor, wall, room)) {
                this.hallwayPosition[0] = new HallwayPosition(temp.hallwayPosition[2].atX, this.roomPosition.startY + this.roomPosition.height, "Vertical",
                        temp.hallwayPosition[2].atY - this.roomPosition.startY - this.roomPosition.height + 1, "Up");
                temp.hallwayPosition[1] = new HallwayPosition(this.hallwayPosition[0].atX, this.hallwayPosition[0].atY + this.hallwayPosition[0].len - 1, "Vertical", this.hallwayPosition[0].len, "Down");
                Hallway.AddHallway(world, floor, wall, this.hallwayPosition[0]);
                Hallway.AddHallway(world, floor, wall, temp.hallwayPosition[2]);
                world[temp.hallwayPosition[2].atX + 1][temp.hallwayPosition[2].atY + 1] = wall;
                break;
            }
            if(temp.findLevelRoomRight(world, floor, wall, room)) {
                this.hallwayPosition[0] = new HallwayPosition(temp.hallwayPosition[3].atX, this.roomPosition.startY + this.roomPosition.height, "Vertical",
                        temp.hallwayPosition[3].atY - this.roomPosition.startY - this.roomPosition.height + 1, "Up");
                temp.hallwayPosition[1] = new HallwayPosition(this.hallwayPosition[0].atX, this.hallwayPosition[0].atY + this.hallwayPosition[0].len - 1, "Vertical", this.hallwayPosition[0].len, "Down");
                Hallway.AddHallway(world, floor, wall, this.hallwayPosition[0]);
                Hallway.AddHallway(world, floor, wall, temp.hallwayPosition[3]);
                world[temp.hallwayPosition[3].atX - 1][temp.hallwayPosition[3].atY + 1] = wall;
                break;
            }
        }
    }
}
