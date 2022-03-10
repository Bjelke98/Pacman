package Pacman3.map.wall;

import Pacman3.map.PacPoint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Creates a straight wall piece
 * @author Krister
 */
public class WallStraight extends Line {

    /**
     * Creates a straight wall based on direction and position of the piece.
     * @param direction Direction of the piece
     * @param p Position of the piece
     */
    public WallStraight(int direction, PacPoint p){
        double pHalfX = p.pxX()+ PacPoint.HALF_SCALE;
        double pHalfY = p.pxY()+ PacPoint.HALF_SCALE;
        if(direction==1){
            setStartX(pHalfX);
            setStartY(p.pxY());

            setEndX(pHalfX);
            setEndY(p.pxY()+ PacPoint.SCALE);
        } else {
            setStartX(p.pxX());
            setStartY(pHalfY);

            setEndX(p.pxX()+ PacPoint.SCALE);
            setEndY(pHalfY);
        }
        setStroke(Color.BLUE);
        setStrokeWidth(Math.ceil(PacPoint.SIXTH_SCALE));
    }
}
