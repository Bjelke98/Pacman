package Pacman3.map;

import Pacman3.Settings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class being one cell of the entire grid represented as a rectangle.
 * In DEBUG mode (found in settings) the cells are visable and clickable to see cell information.
 * @author Krister Iversen
 */
public class PacCell extends Rectangle {
    private final CellType cellType;

    private final PacPoint cellPoint;

    private PacCell up;
    private PacCell down;
    private PacCell left;
    private PacCell right;

    private boolean visited = false;

    /**
     * Creates a cell based on position and type
     * @param cellPoint Cell Position
     * @param cellInt Cell Type
     */
    public PacCell(PacPoint cellPoint, int cellInt){
        super(cellPoint.pxX(), cellPoint.pxY(), cellPoint.size(), cellPoint.size());

        this.cellPoint = cellPoint;

        this.cellType = switch (cellInt){
            case 5 -> CellType.WALL;
            case 2 -> CellType.EMPTY;
            default -> throw new IllegalStateException("Unexpected value: " + cellInt);
        };

        //setFill(Color.TRANSPARENT);
        setFill(Color.BLACK);
        //setFill(cellType == CellType.EMPTY ? Color.GREEN : Color.RED);
        //setStroke(cellType == CellType.EMPTY ? Color.GREEN : Color.RED);


        if(Settings.DEBUG){
            setOnMousePressed(e-> printAll());
            if(cellType==CellType.EMPTY){
                setStrokeWidth(2);
                setStroke(Color.GREEN);
            } else {
                setStrokeWidth(2);
                setStroke(Color.RED);
            }
        }
    }

    // Setters used to link object together like a "grid graph"
    public void setUp(PacCell up) {
        this.up = up;
    }
    public void setDown(PacCell down) {
        this.down = down;
    }
    public void setLeft(PacCell left) {
        this.left = left;
    }
    public void setRight(PacCell right) {
        this.right = right;
    }

    // Getters used to return cell being in wanted direction
    public PacCell up() {
        return up;
    }
    public PacCell down() {
        return down;
    }
    public PacCell left() {
        return left;
    }
    public PacCell right() {
        return right;
    }

    /**
     * @return CellType
     */
    public CellType getCellType() {
        return cellType;
    }

    /**
     * Get cell point (x,y on a coordinate scale)
     * @return CellPoint
     */
    public PacPoint getCellPoint() {
        return cellPoint;
    }

    /**
     * @return String of object information
     */
    @Override
    public String toString() {
        return cellPoint.toString();
    }

    /**
     * Print position information
     */
    public void print(){
        cellPoint.print();
    }

    /**
     * Print position information and neighboring cells
     */
    public void printAll(){
        cellPoint.printAll();
    }

    /**
     * @return returns true if cell is a wall
     */
    public boolean isWall(){
        return cellType==CellType.WALL;
    }

    /**
     * Used to check if a cell is visited
     * (Not in use) possible uses: Pathfindning
     */
    public void visit(){
        visited = true;
    }

    /**
     * @return returns true if the cell is visited
     */
    public boolean isVisited(){
        return visited;
    }

    /**
     * Set visited to false
     */
    public void unVisit(){
        visited = false;
    }
}
