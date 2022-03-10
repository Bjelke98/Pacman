package Pacman3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Controller for the full game
 *
 * Level data made using excel
 * Sprites painted using pixel drawing software
 *
 * There is a lot of features i left out for this project because of time constraints.
 *      Something i wish i had time for:
 *          Sound effects.
 *          More "eye candy" (Styling)
 *          Block entrance into ghost spawn for pacman
 *          Improved pathfinding for ghosts
 *
 * This game was made for the Obligatory delivery for OBJ2100 - Object Oriented Programming (Second semester)
 *
 * @author Krister Iversen
 */
public class PacmanController extends Application {

    private Game game;

    private final SideMenu sideMenu = new SideMenu();

    /**
     * Starts the application
     * @param stage Primary stage for the application
     * @throws Exception Prevents crash on unexpected error
     */
    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, Settings.WINDOW_X, Settings.WINDOW_Y);

        game = new Game(scene);

        root.setCenter(game);

        game.setPrefWidth(Settings.GAME_X);
        game.setPrefHeight(Settings.GAME_Y);

        root.setRight(sideMenu);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the application
     * @param args Console arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
