package com.kelnozzxarann.mazegenerator.mazegeneration;

import com.kelnozzxarann.mazegenerator.Maze;

public interface MazeGenerationStrategy {
    Maze generate(Maze maze);
    Maze generate(Maze maze, int seed);
}
