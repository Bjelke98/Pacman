package Pacman3;

import Pacman3.score.Score;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Controller for the game side menu
 * @author Krister Iversen
 */
public class SideMenu extends VBox {

    public static final Score SCORE = new Score(); // Score is static for easy access independent on game state

    /**
     * Score and status pane
     */
    public SideMenu(){
        setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        setPrefWidth(Settings.WINDOW_X-Settings.GAME_X);
        setPrefHeight(Settings.WINDOW_X);
        getChildren().add(SCORE);
    }
}
