package Pacman3.score;

import Pacman3.map.PacPoint;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * Score controller
 * @author Krister Iversen
 */
public class Score extends VBox {

    private static final int SMALL_VALUE = 10;
    private static final int BIG_VALUE = 50;
    private static final int FRUIT_VALUE = 200;
    private static final int ENEMY_VALUE = 200;
    private static final int START_LIVES = 2;

    private static final Leaderboard LB = new Leaderboard("Top Scores:");

    private int smallSnacks;
    private int bigSnacks;
    private int fruitSnacks;
    private int enemiesKilled;
    private int lives;

    private final ScoreLabel smallSnacksLabel = new ScoreLabel();
    private final ScoreLabel bigSnacksLabel = new ScoreLabel();
    private final ScoreLabel fruitSnacksLabel = new ScoreLabel();
    private final ScoreLabel enemiesKilledLabel = new ScoreLabel();
    private final ScoreLabel totalScoreLabel = new ScoreLabel();
    private final ScoreLabel extraLivesLabel = new ScoreLabel();

    /**
     * Creates the Score object containing game state elements.
     */
    public Score(){
        reset();
        getChildren().addAll(smallSnacksLabel, bigSnacksLabel, fruitSnacksLabel, enemiesKilledLabel, totalScoreLabel, extraLivesLabel, LB);
        setPadding(new Insets(PacPoint.FOURTH_SCALE));
    }

    private void updateScore(){
        smallSnacksLabel.setText("Small: "+getSmallScore());
        bigSnacksLabel.setText("Big: "+getBigScore());
        fruitSnacksLabel.setText("Fruit: "+getFruitScore());
        totalScoreLabel.setText("Total: "+getTotalScore());
        enemiesKilledLabel.setText("Enemy Killed: "+getEnemiesKilledScore());
        extraLivesLabel.setText("Extra lives: "+getLives());
    }

    // Getters for all relevant values
    public int getSmallScore(){
        return smallSnacks*SMALL_VALUE;
    }
    public int getBigScore(){
        return bigSnacks*BIG_VALUE;
    }
    public int getFruitScore(){
        return fruitSnacks*FRUIT_VALUE;
    }
    public int getTotalScore(){
        return getSmallScore()+getBigScore()+getFruitScore()+getEnemiesKilledScore();
    }
    public int getLives(){
        return lives;
    }
    public int getEnemiesKilledScore(){return enemiesKilled*ENEMY_VALUE;}

    // Adders for all changeable values
    public void addSmall(){
        smallSnacks++;updateScore();
    }
    public void addBig(){
        bigSnacks++;updateScore();
    }
    public void addFruit(){
        fruitSnacks++;updateScore();
    }
    public void killEnemy(){
        enemiesKilled++;updateScore();
    }

    public void death(){
        lives--;updateScore();
    }

    /**
     * Resets the scoreboard.
     */
    public void reset(){
        addToLeaderboard(getTotalScore());
        smallSnacks = 0;
        bigSnacks = 0;
        fruitSnacks = 0;
        enemiesKilled = 0;
        lives = START_LIVES;
        updateScore();
    }

    private static void addToLeaderboard(int total){
        LB.addScore(total);
    }

}
