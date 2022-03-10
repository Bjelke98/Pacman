package Pacman3.score;

import Pacman3.map.PacPoint;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Containing and managing scores. Stored in SCORE_FILE file.
 * @author Krister Iversen
 */
public class Leaderboard extends VBox {

    private static final String SCORE_FILE = "scores.ser";

    private ArrayList<Integer> highScoreList = null;
    private static final ScoreLabel TITLE = new ScoreLabel();

    /**
     * Creates a scoreboard based on the score file
     * @param title Leaderboard name
     */
    public Leaderboard(String title){
        setPadding(new Insets(PacPoint.HALF_SCALE, 0, PacPoint.HALF_SCALE, 0));
        TITLE.setText(title);
        File f = new File(SCORE_FILE);
        if (!f.exists()){
            highScoreList = new ArrayList<>();
            serialize();
        }
        deSerialize();
        update();
    }

    /**
     * Submits score to the leaderboard. Updates display and saves to file
     * @param score Submitted score
     */
    public void addScore(int score){
        highScoreList.add(score);
        serialize();
        update();
    }

    private void update(){
        getChildren().clear();
        getChildren().addAll(TITLE, new Label());
        if(highScoreList!=null){
            Collections.sort(highScoreList);
            int size = Math.min(highScoreList.size(), 7);
            for (int i = highScoreList.size()-1; i >= highScoreList.size()-size; i--) {
                getChildren().add(new ScoreLabel(""+highScoreList.get(i)));
            }
            // Code for displaying all scores
//            for (Integer s : highScoreList){
//                getChildren().add(new ScoreLabel(""+s));
//            }
        }
    }

    private void serialize(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORE_FILE))){
            oos.writeObject(highScoreList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deSerialize(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SCORE_FILE))){
            highScoreList = (ArrayList<Integer>) ois.readObject();
        } catch (IOException | ClassNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
