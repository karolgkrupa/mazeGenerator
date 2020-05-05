package com.kelnozzxarann.mazegenerator;

import com.kelnozzxarann.mazegenerator.room.Room;

import java.util.ArrayList;
import java.util.Collection;

public class Maze {
    private Collection<Room> rooms;

    public Maze() {
        rooms = new ArrayList<>();
    }

    public Maze(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Collection<Room> getAllRooms() {
        return rooms;
    }
}
