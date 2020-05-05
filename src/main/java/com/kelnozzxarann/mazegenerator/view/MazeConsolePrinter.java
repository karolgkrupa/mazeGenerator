package com.kelnozzxarann.mazegenerator.view;

import com.kelnozzxarann.mazegenerator.Maze;
import com.kelnozzxarann.mazegenerator.border.Wall;
import com.kelnozzxarann.mazegenerator.room.Room;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static com.kelnozzxarann.mazegenerator.Direction.*;

public class MazeConsolePrinter {
    private static final Character COLUMN = '+';
    private static final Character PARALLEL_WALL = '|';
    private static final Character VERTICAL_WALL = '-';
    private static final Character PASSABLE = ' ';

    private int minHeight;
    private int maxHeight;
    private int minWidth;
    private int maxWidth;

    public void printMaze(Maze maze) {
        System.out.println(getMazeString(maze));
    }

    protected String getMazeString(Maze maze) {
        Collection<Room> allRooms = maze.getAllRooms();
        if (allRooms.isEmpty()) {
            return "";
        }
        minHeight = allRooms.stream().map(Room::getPosition).map(ImmutablePair::getLeft).min(Comparator.naturalOrder()).get();
        maxHeight = allRooms.stream().map(Room::getPosition).map(ImmutablePair::getLeft).max(Comparator.naturalOrder()).get();
        minWidth = allRooms.stream().map(Room::getPosition).map(ImmutablePair::getRight).min(Comparator.naturalOrder()).get();
        maxWidth = allRooms.stream().map(Room::getPosition).map(ImmutablePair::getRight).max(Comparator.naturalOrder()).get();

        StringBuilder stringBuilder = new StringBuilder(getMapWidth() * getMapHeight());
        IntStream.range(0, getMapHeight()).forEach(i -> stringBuilder.append(StringUtils.leftPad("\n", getMapWidth(), ' ')));


        for (Room room : allRooms) {
            int yRoomPosition = room.getPosition().getLeft()*2+1;
            int xRoomPosition = room.getPosition().getRight()*2+1;
            int roomCenter = yRoomPosition * getMapWidth() + xRoomPosition;
            columnsPosition(roomCenter).forEach(i -> stringBuilder.setCharAt(i, COLUMN));
            if (room.getBorder(East) instanceof Wall) {
                stringBuilder.setCharAt(roomCenter+1, PARALLEL_WALL);
            }
            if (room.getBorder(West) instanceof Wall) {
                stringBuilder.setCharAt(roomCenter-1, PARALLEL_WALL);
            }
            if (room.getBorder(North) instanceof Wall) {
                stringBuilder.setCharAt(roomCenter-getMapWidth(), VERTICAL_WALL);
            }
            if (room.getBorder(South) instanceof Wall) {
                stringBuilder.setCharAt(roomCenter+getMapWidth(), VERTICAL_WALL);
            }
        }
        return stringBuilder.toString();
    }

    private int getMapHeight() {
        return 2 + (2 * maxHeight+1 - minHeight);
    }

    private int getMapWidth() {
        return 3 + (2 * maxWidth+1 - minWidth);
    }

    private List<Integer> columnsPosition(int roomCenter) {
        List<Integer> integers = new ArrayList<>();
        int coulumnPlacement;
        if ((coulumnPlacement = roomCenter-getMapWidth()-1) >= 0) {
            integers.add(coulumnPlacement);
        }
        if ((coulumnPlacement = roomCenter-getMapWidth()+1) > 0) {
            integers.add(coulumnPlacement);
        }
        if ((coulumnPlacement = roomCenter+getMapWidth()-1) < getMapWidth() * getMapHeight()) {
            integers.add(coulumnPlacement);
        }
        if ((coulumnPlacement = roomCenter+getMapWidth()+1) <= getMapWidth() * getMapHeight()) {
            integers.add(coulumnPlacement);
        }
        return integers;
    }
}
