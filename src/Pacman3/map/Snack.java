package Pacman3.map;

import Pacman3.SideMenu;
import Pacman3.characters.NPCharacter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

/**
 * Contains a snack or powerup and grants point based on value
 * @author Krister Iversen
 */
public class Snack extends Circle {
    public final SnackType snackType;

    /**
     * Creates a snack based on position and type of snack
     * @param p Snack position
     * @param cellType Snack Type
     */
    public Snack(PacPoint p, int cellType){
        super(p.pxX()+ PacPoint.HALF_SCALE, p.pxY()+ PacPoint.HALF_SCALE, findRadius(cellType));
        snackType = findSnackType(cellType);
        setFill(snackType == SnackType.DEFAULT ? Color.ORANGE : Color.YELLOW);
    }
    private static SnackType findSnackType(int cellType){
        return cellType==3 ? SnackType.DEFAULT : SnackType.POWERUP;
    }
    private static double findRadius(int cellType){
        return findRadius(findSnackType(cellType));
    }
    private static double findRadius(SnackType snackType){
        return snackType==SnackType.DEFAULT ? PacPoint.SIXTH_SCALE : PacPoint.HALF_SCALE;
    }

    /**
     * Collects a snack
     * @param enemies Needed for a case where a powerup is picked up.
     */
    public void collect(List<NPCharacter> enemies){
        //setFill(Color.BLACK);
        Pane p = (Pane)getParent();
        p.getChildren().remove(this);

        if(snackType==SnackType.DEFAULT){
            SideMenu.SCORE.addSmall();
        } else {
            SideMenu.SCORE.addBig();
            for (NPCharacter enemy : enemies){
                enemy.startAfraid();
            }
        }
    }
}
