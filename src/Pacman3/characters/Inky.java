package Pacman3.characters;

import Pacman3.Game;
import javafx.scene.image.Image;

/**
 * Creates the Inky (Cyan) character
 * @author Krister Iversen
 */
public class Inky extends NPCharacter{
    private static final Image img = new Image("InkySprite3.png");

    /**
     * Inky constructor.
     * @param game Needed for updating game state
     */
    public Inky(Game game) {
        super(img);
        super.game = game;
        ghost = img;
        startPosX = 12;
        startPosY = 14;
        setPos(startPosX, startPosY);
    }
}
