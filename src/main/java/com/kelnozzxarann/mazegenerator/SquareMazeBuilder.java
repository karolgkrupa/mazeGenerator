package com.kelnozzxarann.mazegenerator;

import com.google.common.collect.Sets;
import com.kelnozzxarann.mazegenerator.mazegeneration.MazeGenerationStrategy;
import com.kelnozzxarann.mazegenerator.room.Room;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kelnozzxarann.mazegenerator.Direction.*;

public class SquareMazeBuilder {
    private Integer width;
    private Integer height;
    private MazeGenerationStrategy mazeGenerationStrategy;

    public SquareMazeBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public SquareMazeBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public SquareMazeBuilder setMazeGenerationStrategy(MazeGenerationStrategy mazeGenerationStrategy) {
        this.mazeGenerationStrategy = mazeGenerationStrategy;
        return this;
    }

    public Maze build() {
        if (isAllRequiredFieldsSet()){
            throw new RuntimeException("Please set required fields");
        }
        List<Room> rooms = Sets.cartesianProduct(
                    IntStream.range(0, height).boxed().collect(Collectors.toSet()),
                    IntStream.range(0, width).boxed().collect(Collectors.toSet())
                ).stream()
                .map(pair -> new ImmutablePair<>(pair.get(0), pair.get(1)))
                .map(Room::new)
                .collect(Collectors.toList());
        createRoomStructure(rooms);
        Maze maze = new Maze(rooms);

        return mazeGenerationStrategy.generate(maze);
    }

    private void createRoomStructure(List<Room> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (rooms.size() > i+1 && (i/width) == ((i+1)/width)) {
                room.setConnection(East, rooms.get(i + 1));
            }
            if (rooms.size() > i+width) {
                room.setConnection(South, rooms.get(i + width));
            }
        }
    }

    private boolean isAllRequiredFieldsSet() {
        return width == null || height == null || mazeGenerationStrategy == null;
    }
}
