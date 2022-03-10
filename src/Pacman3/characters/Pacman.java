package Pacman3.characters;

import Pacman3.Game;
import Pacman3.Settings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * Creates the main character (Pacman)
 * @author Krister Iversen
 */
public class Pacman extends AnimatedCharacter{

    private static final Rectangle2D VIEW_IDLE = new Rectangle2D(0, 0, CHARACTER_SIZE, CHARACTER_SIZE);
    private static final Rectangle2D VIEW_UP = new Rectangle2D(CHARACTER_SIZE, 0, CHARACTER_SIZE, CHARACTER_SIZE);
    private static final Rectangle2D VIEW_DOWN = new Rectangle2D(CHARACTER_SIZE*2, 0, CHARACTER_SIZE, CHARACTER_SIZE);
    private static final Rectangle2D VIEW_LEFT = new Rectangle2D(CHARACTER_SIZE*3, 0, CHARACTER_SIZE, CHARACTER_SIZE);
    private static final Rectangle2D VIEW_RIGHT = new Rectangle2D(CHARACTER_SIZE*4, 0, CHARACTER_SIZE, CHARACTER_SIZE);

    private final Timeline ANIMATION;

    EventHandler<ActionEvent> handleAnimation = e-> {
        Rectangle2D currentView = switch (currentMove){
            case UP -> VIEW_UP;
            case DOWN -> VIEW_DOWN;
            case LEFT -> VIEW_LEFT;
            case RIGHT -> VIEW_RIGHT;
            case IDLE -> VIEW_IDLE;
        };
        setViewport(animationStep==1 ? VIEW_IDLE : currentView);
        animationStep = animationStep==1 ? 2 : 1;
    };

    /**
     * Pacman constructor
     * @param game Needed for game state updates
     */
    public Pacman(Game game) {
        super(new Image("packmanSprite2.png"));

        super.game = game;

        ANIMATION = new Timeline(new KeyFrame(ANIMATION_DURATION, handleAnimation));
        ANIMATION.setCycleCount(Timeline.INDEFINITE);
        ANIMATION.play();

        startPosX = 12;
        startPosY = 17;

        setPos(startPosX, startPosY);

        setFitWidth(SPRITE_SCALE);
        setFitHeight(SPRITE_SCALE);

        if(Settings.DEBUG){
            // Stop animation test
            setOnMouseClicked(e -> {
                if (ANIMATION.getStatus() == Animation.Status.PAUSED) {
                    ANIMATION.play();
                } else {
                    ANIMATION.pause();
                }
            });
        }

        pacman =this;

    }
//    public void up(){
//        setY(getY()-8);
//        nextMove = CharacterState.UP;
//        currentMove = CharacterState.UP;
//    }
//    public void down(){
//        setY(getY()+8);
//        nextMove = CharacterState.DOWN;
//        currentMove = CharacterState.DOWN;
//    }
//    public void left(){
//        setX(getX()-8);
//        nextMove = CharacterState.LEFT;
//        currentMove = CharacterState.LEFT;
//    }
//    public void right(){
//        setX(getX()+8);
//        nextMove = CharacterState.RIGHT;
//        currentMove = CharacterState.RIGHT;
//    }

    /**
     * Pauses Pacman
     */
    @Override
    public void pause() {
        if (ANIMATION.getStatus() != Animation.Status.PAUSED) {
            ANIMATION.pause();
            MOVEMENT.pause();
        }
    }

    /**
     * Starts Pacman
     */
    @Override
    public void play() {
        if (ANIMATION.getStatus() == Animation.Status.PAUSED) {
            ANIMATION.play();
            MOVEMENT.play();
        }
    }

    /**
     * Toggles (Play/Pause) Pacman
     */
    @Override
    public void toggle() {
        if (ANIMATION.getStatus() == Animation.Status.PAUSED) {
            play();
        } else {
            pause();
        }
    }

    /**
     * Tells if AnimatedCharacter is Pacman
     * @return isPackman true
     */
    @Override
    public boolean isPacman() {
        return true;
    }
}
