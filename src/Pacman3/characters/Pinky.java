package Pacman3.characters;

import Pacman3.Game;
import javafx.scene.image.Image;

/**
 * Creates the Pinky (Pink) character
 * @author Krister Iversen
 */
public class Pinky extends NPCharacter{
    private static final Image img = new Image("pinkySprite3.png");

    /**
     * Pinky constructor.
     * @param game Needed for updating game state
     */
    public Pinky(Game game) {
        super(img);
        super.game = game;
        ghost = img;
        startPosX = 11;
        startPosY = 14;
        setPos(startPosX, startPosY);
    }
}
