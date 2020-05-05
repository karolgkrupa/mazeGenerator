package com.kelnozzxarann.mazegenerator.mazegeneration;

import com.kelnozzxarann.mazegenerator.Direction;
import com.kelnozzxarann.mazegenerator.Maze;
import com.kelnozzxarann.mazegenerator.border.Door;
import com.kelnozzxarann.mazegenerator.room.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class RecursiveBacktrackerMazeGenerationStrategy implements MazeGenerationStrategy {
    private final Logger logger = LogManager.getLogger();
    private Random random;
    private Set<Room> notVisited = new HashSet<>();
    private Stack<Room> visited = new Stack<>();

    @Override
    public Maze generate(Maze maze) {
        List<Room> allRooms = new ArrayList<>(maze.getAllRooms());
        notVisited.addAll(allRooms);
        random = new Random();


        Room startingRoom = allRooms.get(random.nextInt(allRooms.size()));
        visited.push(startingRoom);
        notVisited.remove(startingRoom);
        while(!visited.isEmpty()) {
            Room currentRoom = visited.peek();
            logger.info("I am in currently in {}", currentRoom.getPosition());
            List<Room> availableDirections = currentRoom.getAllConnectedRooms().stream()
                    .filter(room -> notVisited.contains(room))
                    .collect(Collectors.toList());
            if (availableDirections.isEmpty()) {
                logger.info("There is currently no new rooms to enter");
                visited.pop();
                continue;
            }
            Room nextRoom = availableDirections.get(random.nextInt(availableDirections.size()));
            currentRoom.setBorder(nextRoom, new Door());
            notVisited.remove(nextRoom);
            visited.push(nextRoom);
            logger.info("I am moving to room {}", nextRoom.getPosition());
        }
        return maze;
    }

    private void recursiveDig(Room room) {
        if(visited.isEmpty()) {
            return;
        }

        List<Direction> availableDirections = room.getConnectedDirections();
        if (availableDirections.isEmpty()) {
            Room previousRoom = visited.pop();
            recursiveDig(previousRoom);
        }

        Room newRoom = null;
        while(newRoom == null) {
            Direction direction = availableDirections.get(random.nextInt(availableDirections.size()));
            Room roomInDirection = room.getRoomInDirection(direction);
            if(notVisited.contains(roomInDirection)) {
                newRoom = roomInDirection;
                notVisited.remove(newRoom);
            } else {
                availableDirections.remove(direction);
            }
        }
        visited.push(newRoom);
        notVisited.remove(newRoom);
        recursiveDig(newRoom);
    }


    @Override
    public Maze generate(Maze maze, int seed) {
        return null;
    }
}
