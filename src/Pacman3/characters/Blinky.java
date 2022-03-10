package Pacman3.characters;

import Pacman3.Game;
import javafx.scene.image.Image;

/**
 * Creates the Blinky (Red) character
 * @author Krister Iversen
 */
public class Blinky extends NPCharacter{
    private static final Image img = new Image("blinkySprite3.png");

    /**
     * Blinky constructor.
     * @param game Needed for updating game state
     */
    public Blinky(Game game) {
        super(img);
        super.game = game;
        ghost = img;
        startPosX = 10;
        startPosY = 14;
        setPos(startPosX, startPosY);
    }
}
