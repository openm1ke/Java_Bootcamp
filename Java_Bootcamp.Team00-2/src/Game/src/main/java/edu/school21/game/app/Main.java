package edu.school21.game.app;

import org.logic.GameParser;

import com.beust.jcommander.JCommander;

import org.logic.Parser;
import org.logic.Parametrs;
import org.logic.Point;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Parser parsArgs = new Parser();
        JCommander argsJC = JCommander.newBuilder().addObject(parsArgs).build();
        argsJC.parse(args);
        Parametrs parametrs = new Parametrs(parsArgs.profile);
        GameParser gameParser = new GameParser(parsArgs, parametrs);
        gameParser.gameCycle();
    }
}