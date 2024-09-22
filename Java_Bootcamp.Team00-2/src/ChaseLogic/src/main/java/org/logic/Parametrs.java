package org.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Parametrs {
    public boolean debug = false;
    public final String enemy;
    public final String player;
    public final String goal;
    public final String wall;
    public final String empty;
    public final String colorEnemy;
    public final String colorPlayer;
    public final String colorGoal;
    public final String colorWall;
    public final String colorEmpty;
    public final String border;

    public Parametrs(String profile){
        if (profile.equals("dev")){
            debug = true;
        }

        Properties properties = new Properties();
        try {
            System.out.println(new File("").getAbsoluteFile());
            properties.load(this.getClass().getClassLoader().getResourceAsStream("application-" + profile + ".properties"));
        } catch (IOException e){
//            System.err.println("not Cool");
            throw new RuntimeException(e);
        }
        this.enemy = properties.getProperty("enemy.char");
        this.player = properties.getProperty("player.char");
        this.goal = properties.getProperty("goal.char");
        this.wall = properties.getProperty("wall.char");

        String emptyChar = properties.getProperty("empty.char");
        if (emptyChar != null && !emptyChar.isEmpty()) {
            this.empty = emptyChar;
        } else {
            this.empty = " ";
        }
        this.border = properties.getProperty("border.char");
        this.colorEnemy = properties.getProperty("enemy.color");
        this.colorPlayer =properties.getProperty("player.color");
        this.colorGoal = properties.getProperty("goal.color");
        this.colorWall = properties.getProperty("wall.color");
        this.colorEmpty = properties.getProperty("empty.color");
//        System.out.println("Enemy: " + enemy + " player: " + player + " goal: " + goal + " wall: " + wall + " empty: " + empty);
//        System.out.println("COLOR");
//        System.out.println("Enemy: " + colorEnemy + " player: " + colorPlayer + " goal: " + colorGoal + " wall: " + colorWall + " empty: " + colorEmpty);
    }

}
