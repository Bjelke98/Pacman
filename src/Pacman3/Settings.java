package Pacman3;

import Pacman3.map.PacPoint;

/**
 * Global variables used globally in the game
 * @author Krister Iversen
 */
public class Settings {
    public static final double GAME_X = 24* PacPoint.SCALE;
    public static final double GAME_Y = 31* PacPoint.SCALE;
    public static final double WINDOW_X = (24+8)* PacPoint.SCALE;
    public static final double WINDOW_Y = 31* PacPoint.SCALE;
    public static final boolean DEBUG = false;
}
