package com.kelnozzxarann.mazegenerator.view;


import com.kelnozzxarann.mazegenerator.Direction;
import com.kelnozzxarann.mazegenerator.Maze;
import com.kelnozzxarann.mazegenerator.SquareMazeBuilder;
import com.kelnozzxarann.mazegenerator.border.Door;
import com.kelnozzxarann.mazegenerator.mazegeneration.MazeGenerationStrategy;
import com.kelnozzxarann.mazegenerator.room.Room;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MazeConsolePrinterTest {

    @Test
    void printMaze() {
        MazeGenerationStrategy mazeGenerationStrategy = new MazeGenerationStrategy() {
            @Override
            public Maze generate(Maze maze) {
                return maze;
            }

            @Override
            public Maze generate(Maze maze, int seed) {
                return maze;
            }
        };
        Maze maze = new SquareMazeBuilder().setHeight(4).setWidth(5).setMazeGenerationStrategy(mazeGenerationStrategy).build();
        String result = new MazeConsolePrinter().getMazeString(maze);

        String expected = "+-+-+-+-+-+\n" +
                "| | | | | |\n" +
                "+-+-+-+-+-+\n" +
                "| | | | | |\n" +
                "+-+-+-+-+-+\n" +
                "| | | | | |\n" +
                "+-+-+-+-+-+\n" +
                "| | | | | |\n" +
                "+-+-+-+-+-+";
        assertEquals(expected, result);
    }

    @Test
    void printMazeWithDoors() {
        MazeGenerationStrategy mazeGenerationStrategy = new MazeGenerationStrategy() {
            @Override
            public Maze generate(Maze maze) {
                return maze;
            }

            @Override
            public Maze generate(Maze maze, int seed) {
                return maze;
            }
        };
        Maze maze = new SquareMazeBuilder().setHeight(4).setWidth(5).setMazeGenerationStrategy(mazeGenerationStrategy).build();
        List<Room> allRooms = new ArrayList(maze.getAllRooms());

        allRooms.get(0).setBorder(Direction.South, new Door());
        allRooms.get(5).setBorder(Direction.East, new Door());
        allRooms.get(5).setBorder(Direction.West, new Door());
        allRooms.get(6).setBorder(Direction.East, new Door());

        String result = new MazeConsolePrinter().getMazeString(maze);

        String expected = "+-+-+-+-+-+\n" +
                          "| | | | | |\n" +
                          "+ +-+-+-+-+\n" +
                          "|     | | |\n" +
                          "+-+-+-+-+-+\n" +
                          "| | | | | |\n" +
                          "+-+-+-+-+-+\n" +
                          "| | | | | |\n" +
                          "+-+-+-+-+-+\n";
        assertEquals(expected, result);
    }
}