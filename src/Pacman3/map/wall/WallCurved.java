package Pacman3.map.wall;

import Pacman3.map.PacPoint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

/**
 * Creates a curved wall piece
 * @author Krister Iversen
 */
public class WallCurved extends Arc {

    /**
     * Creates the curved wall based on direction and position
     * @param direction Direction of piece
     * @param p Placement of piece
     */
    public WallCurved(int direction, PacPoint p){
        super();
        setLength(90);
        setRadiusX(PacPoint.HALF_SCALE);
        setRadiusY(PacPoint.HALF_SCALE);
        int angle = switch (direction){
            case 1 -> 0;
            case 2 -> 90;
            case 3 -> 180;
            case 4 -> 270;
            default -> throw new IllegalArgumentException("direction" + direction);
        };
        double x = switch (direction){
            case 1, 4 -> 0;
            case 2, 3 -> PacPoint.SCALE;
            default -> throw new IllegalArgumentException("direction" + direction);
        };
        double y = switch (direction){
            case 1, 2 -> PacPoint.SCALE;
            case 3, 4 -> 0;
            default -> throw new IllegalArgumentException("direction" + direction);
        };

        setStartAngle(angle);
        setCenterX(p.pxX()+x);
        setCenterY(p.pxY()+y);
//        setStroke(Color.ORANGE);
        setStroke(Color.BLUE);
        setStrokeWidth(Math.ceil(PacPoint.SIXTH_SCALE));
    }
}
