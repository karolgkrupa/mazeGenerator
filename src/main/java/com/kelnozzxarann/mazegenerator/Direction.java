package com.kelnozzxarann.mazegenerator;

import jdk.jshell.spi.ExecutionControl;

public enum Direction {
    North,
    East,
    West,
    South;

    public Direction opposite() {
        switch (this){
            case North: return South;
            case East: return West;
            case West: return East;
            case South: return North;
            default: throw new RuntimeException("The opposite direction has not been defined");
        }
    }
}
