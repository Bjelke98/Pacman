package Pacman3.characters;

import Pacman3.Game;
import Pacman3.SideMenu;
import Pacman3.map.PacCell;
import Pacman3.map.PacPoint;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Superclass for all animated characters (Pacman, Blinky, Pinky, Inky and Clyde)
 * @author Krister Iversen
 */
public abstract class AnimatedCharacter extends ImageView {

    protected static LinkedList<NPCharacter> enemies = new LinkedList<>();

    protected static Pacman pacman;

    protected Game game;

    protected static final int CHARACTER_SIZE = 48;
    protected static final Duration ANIMATION_DURATION = Duration.millis(100);

    private static final Duration MOVEMENT_DURATION = Duration.millis(20);

    protected int animationStep = 1;

    protected double baseMS = PacPoint.BASE_SCALE;

    public final double SPRITE_SCALE = PacPoint.SCALE*1.75;
    public final double SPRITE_OFFSET = (SPRITE_SCALE- PacPoint.SCALE)/2;

    protected CharacterState currentMove = CharacterState.IDLE;
    protected CharacterState nextMove = CharacterState.IDLE;

    protected final Timeline MOVEMENT;

    protected int startPosX = 1;
    protected int startPosY = 1;

    protected boolean afraidState = false;

    // Trying out pathfinding using breath first. (Incomplete)
    private void pathfind(){
        PacCell packmanCell = pacman.getCurrentCell();
        if (packmanCell.equals(getCurrentCell())){
            return;
        }

        ArrayList<PacCell> current = new ArrayList<>();
        ArrayList<PacCell> next = new ArrayList<>();
        current.add(packmanCell);

        while (true){
            for (PacCell c : current){
                c.visit();
                PacCell[] dir = {
                    c.up(),
                    c.down(),
                    c.left(),
                    c.right()
                };

                for (int i = 0; i<dir.length; i++){
                    if(dir[i]!=null){
                        if(!dir[i].isVisited()){
                            next.add(dir[i]);
                            if(dir[i].equals(getCurrentCell())){
                                switch (i){
                                    case 0-> down();
                                    case 1-> up();
                                    case 2-> right();
                                    case 3-> left();
                                }
                                System.out.println("PackFound");
                                return;
                            }
                        }
                    }
                }

            }
            current.clear();
            current.addAll(next);
            next.clear();
        }

    }

    /**
     * Using a simple pathfinding logic picing random directions for all animated character that has not reimplemented this method.
     * (Not done) Becouse of time constraints i chose to not complete the ghosts pathfinding algorythm. It can easily be implemented overriding the pathfind method in the subclass.
     * @param simple Used to overload previous method
     * @param c Contains current cell pont of the character
     */
    public void pathfind(boolean simple, PacCell c){
        int direction = 5;
        while (!isWalkable(direction, c)){
            direction = (int)(Math.random()*4);
            switch (direction){ // Stop oposit move
                case 0-> {
                    if(currentMove==CharacterState.UP) direction = 5;
                }
                case 1-> {
                    if(currentMove==CharacterState.DOWN) direction = 5;
                }
                case 2-> {
                    if(currentMove==CharacterState.LEFT) direction = 5;
                }
                case 3-> {
                    if(currentMove==CharacterState.RIGHT) direction = 5;
                }
            }
        }
        switch (direction){
            case 0-> down();
            case 1-> up();
            case 2-> right();
            case 3-> left();
        }
    }

    private boolean isWalkable(int d, PacCell c){
        return switch (d){
            case 0-> !cellIsWall(c.down());
            case 1-> !cellIsWall(c.up());
            case 2-> !cellIsWall(c.right());
            case 3-> !cellIsWall(c.left());
            default -> false;
        };
    }

    EventHandler<ActionEvent> handleMovement = e-> {
        PacCell currentCell = getCurrentCell();
        double x = getX()+SPRITE_OFFSET;
        double y = getY()+SPRITE_OFFSET;
        if(x% PacPoint.SCALE==0 && y% PacPoint.SCALE==0){

            // Teleport
            if(currentCell==null){
                double ax = getX();
                double ay = getY();
                double bx = PacPoint.BOARD_BOUNDS_UPSCALED.getMaxX();
                double by = PacPoint.BOARD_BOUNDS_UPSCALED.getMaxY();
                if(ax>bx){
                    setX(ax-bx-PacPoint.SCALE);
                } else
                if(ay>by){
                    setY(ay-by-PacPoint.SCALE);
                } else
                if(ax<PacPoint.BOARD_BOUNDS_UPSCALED.getMinX()){
                    setX(ax+bx+PacPoint.SCALE);
                } else
                if(ay<PacPoint.BOARD_BOUNDS_UPSCALED.getMinY()){
                    setY(ay+by+PacPoint.SCALE);
                }
                currentCell = getCurrentCell();
            }

            PacPoint currentPoint = currentCell.getCellPoint();
            if(!isPacman()){
                pathfind(true, currentCell);
            } else {
                for(NPCharacter enemy : enemies){
                    double enemyHitSize = PacPoint.SIXTH_SCALE;
                    double halfEnemyHitSize = enemyHitSize/2;
                    double centerX = enemy.getX()+PacPoint.HALF_SCALE-halfEnemyHitSize;
                    double centerY = enemy.getY()+PacPoint.HALF_SCALE-halfEnemyHitSize;
                    if(this.getBoundsInLocal().intersects(centerX, centerY, enemyHitSize, enemyHitSize)){
                        if(enemy.isAfraid()){
                            enemy.kill();
                        } else {
                            SideMenu.SCORE.death();
                            if(SideMenu.SCORE.getLives()<0){
                                game.restart();
                            } else {
                                game.death();
                            }
                        }
                    }
                }
                if(game.map.snacks.containsKey(currentPoint)){
                    game.map.snacks.get(currentPoint).collect(enemies);
                    game.map.snacks.remove(currentPoint);
                }
                if(game.map.snacks.isEmpty()){
                    game.nextMap();
                }
            }

            if(currentMove!=nextMove) {
                switch (nextMove) {
                    case UP -> moveChange(currentCell.up());
                    case DOWN -> moveChange(currentCell.down());
                    case LEFT -> moveChange(currentCell.left());
                    case RIGHT -> moveChange(currentCell.right());
                }
            }
            switch (currentMove){
                case UP-> {
                    if(cellIsWall(currentCell.up())){
                        setIdle();
                    }
                }
                case DOWN-> {
                    if(cellIsWall(currentCell.down())){
                        setIdle();
                    }
                }
                case LEFT-> {
                    if(cellIsWall(currentCell.left())){
                        setIdle();
                    }
                }
                case RIGHT-> {
                    if(cellIsWall(currentCell.right())){
                        setIdle();
                    }
                }
            }

        }
        switch (currentMove){
            case UP-> moveUp();
            case DOWN-> moveDown();
            case LEFT-> moveLeft();
            case RIGHT-> moveRight();
        }
    };

    private void setIdle(){
        currentMove=CharacterState.IDLE;
        nextMove=CharacterState.IDLE;
    }

    private void moveChange(PacCell c){
        if(!cellIsWall(c)){
            currentMove=nextMove;
        }
    }

    private boolean cellIsWall(PacCell c){
        if(c==null){
            return false;
        }
        return c.isWall();
    }

    /**
     * Creates the animated character based on sprite provided.
     * @param sprite Image provided as character sprite
     */
    AnimatedCharacter(Image sprite){
        super(sprite);
        MOVEMENT = new Timeline(new KeyFrame(MOVEMENT_DURATION, handleMovement));
        MOVEMENT.setCycleCount(Timeline.INDEFINITE);
        MOVEMENT.play();
    }

    /**
     * Return the current cell of an animated character
     * @return PacCell (X, Y) coordinates
     */
    public PacCell getCurrentCell(){
        return game.map.mapCells.get(new PacPoint((int)((getX()+SPRITE_OFFSET)/ PacPoint.SCALE), (int)((getY()+SPRITE_OFFSET)/ PacPoint.SCALE)));
    }
    private void moveUp(){
        setY(getY()-baseMS);
    }
    private void moveDown(){
        setY(getY()+baseMS);
    }
    private void moveLeft(){
        setX(getX()-baseMS);
    }
    private void moveRight(){
        setX(getX()+baseMS);
    }

    /**
     * Changes the next move to UP for this character.
     */
    public void up(){
        nextMove = CharacterState.UP;
    }
    /**
     * Changes the next move to DOWN for this character.
     */
    public void down(){
        nextMove = CharacterState.DOWN;
    }
    /**
     * Changes the next move to LEFT for this character.
     */
    public void left(){
        nextMove = CharacterState.LEFT;
    }
    /**
     * Changes the next move to RIGHT for this character.
     */
    public void right(){
        nextMove = CharacterState.RIGHT;
    }

    /**
     * Set a new position for an AnimatedCharacter based on Grid coordinates
     * @param x Coordinate X
     * @param y Coordinate Y
     */
    public void setPos(int x, int y){
        setX(x* PacPoint.SCALE-SPRITE_OFFSET);
        setY(y* PacPoint.SCALE-SPRITE_OFFSET);
    }

    /**
     * Stop animation and movement
     */
    public abstract void pause();
    /**
     * Start animation and movement
     */
    public abstract void play();
    /**
     * Start/Stop animation and movement
     */
    public abstract void toggle();

    /**
     * Tells if the current character is Pacman
     * @return Is this character pacman
     */
    public abstract boolean isPacman();

    /**
     * Set a non Pacman entity to be afraid of Pacman
     */
    public void startAfraid(){
        System.out.println("This character cannot be afraid");
    }
    /**
     * Set a non Pacman entity to stop being afraid of Pacman
     */
    public void stopAfraid(){
        System.out.println("This character cannot be afraid");
    }

    /**
     * Kills a character
     */
    public void kill(){
        System.out.println("This character cannot be killed at this point");
    }

    /**
     * Resets character to starting position.
     */
    public void restart(){
        setPos(startPosX, startPosY);
        currentMove = CharacterState.IDLE;
        nextMove = CharacterState.IDLE;
        if(!isPacman())stopAfraid();
    }

    /**
     * Tells if this character is afraid
     * @return is this character afraid
     */
    public boolean isAfraid(){
        return afraidState;
    }

}
