package Pacman3.characters;

import Pacman3.Settings;
import Pacman3.SideMenu;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public abstract class NPCharacter extends AnimatedCharacter{

    private static final Image AFRAID = new Image("afraidSprite.png");

    private static final Rectangle2D[][] VIEWS = new Rectangle2D[][]{{
                new Rectangle2D(0, 0, CHARACTER_SIZE, CHARACTER_SIZE),
                new Rectangle2D(0, CHARACTER_SIZE, CHARACTER_SIZE, CHARACTER_SIZE)},
            {
                new Rectangle2D(CHARACTER_SIZE, 0, CHARACTER_SIZE, CHARACTER_SIZE),
                new Rectangle2D(CHARACTER_SIZE, CHARACTER_SIZE, CHARACTER_SIZE, CHARACTER_SIZE)},
            {
                new Rectangle2D(CHARACTER_SIZE*2, 0, CHARACTER_SIZE, CHARACTER_SIZE),
                new Rectangle2D(CHARACTER_SIZE*2, CHARACTER_SIZE, CHARACTER_SIZE, CHARACTER_SIZE)},
            {
                new Rectangle2D(CHARACTER_SIZE*3, 0, CHARACTER_SIZE, CHARACTER_SIZE),
                new Rectangle2D(CHARACTER_SIZE*3, CHARACTER_SIZE, CHARACTER_SIZE, CHARACTER_SIZE)},
            {
                new Rectangle2D(CHARACTER_SIZE*4, 0, CHARACTER_SIZE, CHARACTER_SIZE),
                new Rectangle2D(CHARACTER_SIZE*4, CHARACTER_SIZE, CHARACTER_SIZE, CHARACTER_SIZE)}
    };

    protected Image ghost;

    private final Timeline ANIMATION;

    private final Timeline AFRAID_ANIMATION;

    private static final Duration AFRAID_DURATION = Duration.millis(7000);

    EventHandler<ActionEvent> handleAnimation = e-> {
        int currentView = switch (currentMove){
            case UP -> 1;
            case DOWN -> 2;
            case LEFT -> 3;
            case RIGHT -> 4;
            case IDLE -> 0;
        };
        setViewport(VIEWS[currentView][animationStep-1]);
        animationStep = animationStep==1 ? 2 : 1;
    };

    EventHandler<ActionEvent> handleAfraidAnimation = e-> {
        stopAfraid();
    };

    NPCharacter(Image sprite) { // Package only
        super(sprite);
        enemies.add(this);

        ANIMATION = new Timeline(new KeyFrame(ANIMATION_DURATION, handleAnimation));
        ANIMATION.setCycleCount(Timeline.INDEFINITE);
        ANIMATION.play();

        AFRAID_ANIMATION = new Timeline(new KeyFrame(AFRAID_DURATION, handleAfraidAnimation));
        AFRAID_ANIMATION.setCycleCount(1);

        setFitWidth(SPRITE_SCALE);
        setFitHeight(SPRITE_SCALE);

        if(Settings.DEBUG){
            // Stop animation test
            setOnMouseClicked(e -> {
                if (ANIMATION.getStatus() == Animation.Status.PAUSED) {
                    ANIMATION.play();
                    MOVEMENT.play();
                } else {
                    ANIMATION.pause();
                    MOVEMENT.pause();
                }
            });
        }
    }
    @Override
    public void pause() {
        if (ANIMATION.getStatus() != Animation.Status.PAUSED) {
            ANIMATION.pause();
            MOVEMENT.pause();
        }
    }

    @Override
    public void play() {
        if (ANIMATION.getStatus() == Animation.Status.PAUSED) {
            ANIMATION.play();
            MOVEMENT.play();
        }
    }

    @Override
    public void toggle() {
        if (ANIMATION.getStatus() == Animation.Status.PAUSED) {
            play();
        } else {
            pause();
        }
    }

    @Override
    public boolean isPacman() {
        return false;
    }

    @Override
    public void startAfraid() {
        stopAfraid();
        setImage(AFRAID);
        afraidState = true;
        AFRAID_ANIMATION.play();
    }

    @Override
    public void stopAfraid() {
        stopAfraidAnimation();
        afraidState = false;
        setImage(ghost);
    }

    private void stopAfraidAnimation(){
        if(AFRAID_ANIMATION.getStatus()!=Animation.Status.STOPPED){
            AFRAID_ANIMATION.stop();
        }
    }

    @Override
    public void kill() {
        SideMenu.SCORE.killEnemy();
        restart();
    }
}
