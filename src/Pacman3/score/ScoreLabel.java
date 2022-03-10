package Pacman3.score;

import Pacman3.map.PacPoint;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Common label for all statistics.
 * @author Krister Iversen
 */
public class ScoreLabel extends Label {

    public static Font font = Font.font("Arial", FontWeight.BOLD, PacPoint.SCALE*0.75); // Game font

    /**
     * Creates an empty Label using the game font
     */
    public ScoreLabel(){
        setFont(font);
    }

    /**
     * Creates a label containing "text" using the game font
     * @param text Text to display
     */
    public ScoreLabel(String text){
        this();
        setText(text);
    }
}
