package com.kelnozzxarann.mazegenerator.room;

import com.kelnozzxarann.mazegenerator.Direction;
import com.kelnozzxarann.mazegenerator.border.Border;
import com.kelnozzxarann.mazegenerator.border.Wall;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.stream.Collectors;

public class Room {
    private final ImmutablePair<Integer, Integer> position;
    private final Map<Direction, RoomConnection> roomConnections;

    public Room(ImmutablePair<Integer, Integer> position) {
        this.position = position;
        roomConnections = new HashMap<>(4);
    }

    public void setConnection(Direction direction, Room room) {
        RoomConnection connection = new RoomConnection(this, room);
        roomConnections.put(direction, connection);
        room.setOneWayConnection(direction.opposite(), connection);
    }

    protected void setOneWayConnection(Direction direction, RoomConnection connection) {
        roomConnections.put(direction, connection);
    }

    public Room getRoomInDirection(Direction direction) {
        return Optional.ofNullable(roomConnections.get(direction)).map(roomConnection -> roomConnection.getConnectedRoom(this)).orElse(null);
    }

    public Border getBorder(Direction direction) {
        return Optional.ofNullable(roomConnections.get(direction))
                .map(RoomConnection::getBorder)
                .orElseGet(Wall::new);
    }

    public void setBorder(Direction direction, Border border) {
        Optional.ofNullable(roomConnections.get(direction)).ifPresent(connection -> connection.setBorder(border));
    }

    public void setBorder(Room room, Border border) {
        List<RoomConnection> collect = roomConnections.values().stream().filter(connection -> connection.getConnectedRoom(this).equals(room)).collect(Collectors.toList());
        collect.forEach(connection -> connection.setBorder(border));
    }

    public List<Direction> getConnectedDirections(){
        return new ArrayList<>(roomConnections.keySet());
    }

    public List<Room> getAllConnectedRooms() {
        return roomConnections.values().stream()
                .map(connection -> connection.getConnectedRoom(this))
                .collect(Collectors.toList());
    }

    public ImmutablePair<Integer, Integer> getPosition() {
        return position;
    }

    private class RoomConnection {
        private final Room[] connecting;
        private Border border;

        public RoomConnection(Room firstRoom, Room secondRoom, Border border) {
            this.connecting = new Room[2];
            connecting[0] = firstRoom;
            connecting[1] = secondRoom;
            this.border = border;
        }

        public RoomConnection(Room firstRoom, Room secondRoom) {
            this(firstRoom, secondRoom, new Wall());
        }

        public Room getConnectedRoom(Room room){
            if (connecting[0].equals(room)) {
                return connecting[1];
            } else if(connecting[1].equals(room)) {
                return connecting[0];
            }
            throw new IllegalArgumentException("Room provided is not a part of this connection!");
        }

        public Border getBorder() {
            return border;
        }

        public void setBorder(Border border) {
            this.border = border;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (position != null ? !position.equals(room.position) : room.position != null) return false;
        return roomConnections.equals(room.roomConnections);
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + roomConnections.hashCode();
        return result;
    }
}
