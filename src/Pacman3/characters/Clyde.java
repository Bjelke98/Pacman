package Pacman3.characters;

import Pacman3.Game;
import javafx.scene.image.Image;

/**
 * Creates the Clyde (Orange) character
 * @author Krister Iversen
 */
public class Clyde extends NPCharacter{
    private static final Image img = new Image("clydeSprite3.png");

    /**
     * Clyde constructor.
     * @param game Needed for updating game state
     */
    public Clyde(Game game) {
        super(img);
        super.game = game;
        ghost = img;
        startPosX = 13;
        startPosY = 14;
        setPos(startPosX, startPosY);
    }
}
