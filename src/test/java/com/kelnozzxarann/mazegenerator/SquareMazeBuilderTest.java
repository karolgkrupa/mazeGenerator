package com.kelnozzxarann.mazegenerator;

import com.kelnozzxarann.mazegenerator.mazegeneration.RecursiveBacktrackerMazeGenerationStrategy;
import com.kelnozzxarann.mazegenerator.view.MazeConsolePrinter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.junit.jupiter.api.Test;


class SquareMazeBuilderTest {
    private MazeConsolePrinter printer = new MazeConsolePrinter();

    private  SquareMazeBuilder squareMazeBuilder;

    @Test
    void thatBuilderWorks() {
        ConfigurationBuilder<BuiltConfiguration> builder
                = ConfigurationBuilderFactory.newConfigurationBuilder();
        AppenderComponentBuilder console
                = builder.newAppender("stdout", "Console");
        builder.add(console);

        RootLoggerComponentBuilder rootLogger
                = builder.newRootLogger(Level.INFO);
        rootLogger.add(builder.newAppenderRef("stdout"));

        builder.add(rootLogger);

        Configurator.initialize(builder.build());

        squareMazeBuilder = new SquareMazeBuilder();
        squareMazeBuilder.setHeight(20)
                .setWidth(20)
                .setMazeGenerationStrategy(new RecursiveBacktrackerMazeGenerationStrategy());
        Maze maze = squareMazeBuilder.build();
        printer.printMaze(maze);
    }
}