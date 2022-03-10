package Pacman3;

import Pacman3.characters.*;
import Pacman3.map.PacMap;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.File;

/**
 * Game controller, controls all game actions at the top level
 * @author Krister Iversen
 */
public class Game extends Pane{

    private static final File[] levels = new File[]{
            new File("level9.csv"),
            new File("level8.csv"),
            new File("level5.txt")
    };

    private int currentLevel = 0;

    private final Pacman pacman = new Pacman(this);
    private final Blinky blinky = new Blinky(this);
    private final Pinky pinky = new Pinky(this);
    private final Inky inky = new Inky(this);
    private final Clyde clyde = new Clyde(this);

    public PacMap map = new PacMap(levels[0], pacman, blinky, pinky, inky, clyde); // Contains the game map

    /**
     * Creates the game pane
     * @param scene Scene needed to set key press handler.
     */
    public Game(Scene scene){

        loadMap();

        scene.setOnKeyPressed(e->{
            switch (e.getCode()){
                case UP-> pacman.up();
                case DOWN-> pacman.down();
                case LEFT-> pacman.left();
                case RIGHT-> pacman.right();
                case SPACE-> toggle();
                case BACK_SPACE -> stop();
                case R -> restart();
                default -> {
                    if(Settings.DEBUG){
                        System.out.println("Not a keybind");
                    }
                }
            }
            if(Settings.DEBUG) {
                System.out.println(e.getCode());
            }
        });
    }

    /**
     * Pauses all game entities.
     */
    public void pause() {
        pacman.pause();
        blinky.pause();
        pinky.pause();
        inky.pause();
        clyde.pause();
    }

    /**
     * Starts all game entities.
     */
    public void play() {
        pacman.play();
        blinky.play();
        pinky.play();
        inky.play();
        clyde.play();
    }

    /**
     * Toggles (Play/Pause) all game entities. Bound to key "SPACE".
     */
    public void toggle() {
        pacman.toggle();
        blinky.toggle();
        pinky.toggle();
        inky.toggle();
        clyde.toggle();
    }

    /**
     * Kills Pacman
     */
    public void death(){
        resetCharacters();
    }

    /**
     * Fully restarts game and setting scores and lives back to default value.
     */
    public void restart() {
        getChildren().clear();
        map = new PacMap(levels[0], pacman, blinky, pinky, inky, clyde);
        resetCharacters();
        SideMenu.SCORE.reset();
        getChildren().add(map);
    }

    /**
     * Moves all characters to starting position. Used to simulate loss of lives.
     */
    private void resetCharacters(){
        pacman.restart();
        blinky.restart();
        pinky.restart();
        inky.restart();
        clyde.restart();
    }

    /**
     * Change map to next map in the levels array
     */
    public void nextMap(){
        getChildren().clear();
        currentLevel++;
        if(currentLevel>levels.length-1){
            currentLevel=0;
        }
        map = new PacMap(levels[currentLevel], pacman, blinky, pinky, inky, clyde);
        resetCharacters();
        getChildren().add(map);
    }

    /**
     * Stops the game. (Not finished)
     */
    public void stop(){

    }

    /**
     * Loads the map pane
     */
    public void loadMap() {
        getChildren().add(map);
    }
}
