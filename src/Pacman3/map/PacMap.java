package Pacman3.map;

import Pacman3.characters.*;
import Pacman3.map.wall.WallCurved;
import Pacman3.map.wall.WallStraight;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Pane containing all the map cells, characters and controls the game board.
 * @author Krister Iversen
 */
public class PacMap extends Pane {

    private final File level;
    public final ArrayList<Node> lines = new ArrayList<>();
    public final HashMap<PacPoint, PacCell> mapCells = new HashMap<>(868);
    public final HashMap<PacPoint, Snack> snacks = new HashMap<>();

    Pacman pacman;
    Blinky blinky;
    Pinky pinky;
    Inky inky;
    Clyde clyde;


    /**
     * Creates the game map
     * @param level First level File
     * @param pacman Character used in the game
     * @param blinky Character used in the game
     * @param pinky Character used in the game
     * @param inky Character used in the game
     * @param clyde Character used in the game
     */
    public PacMap(File level, Pacman pacman, Blinky blinky, Pinky pinky, Inky inky, Clyde clyde){
        this.level = level;
        loadCells();
        connectCells();
        createLines();

        this.pacman = pacman;
        this.blinky = blinky;
        this.pinky = pinky;
        this.inky = inky;
        this.clyde = clyde;

        getChildren().add(pacman);
        getChildren().addAll(blinky,pinky,inky,clyde);
    }

    private void loadCells(){
        try {
            Scanner s = new Scanner(level);
            for (int i = 0; i < ((int) PacPoint.BOARD_BOUNDS.getMaxY())+1; i++) {
                Scanner row = new Scanner(s.nextLine());
                row.useDelimiter(";");
                for (int j = 0; j < ((int) PacPoint.BOARD_BOUNDS.getMaxX())+1; j++) {
                    PacPoint p = new PacPoint(j, i);
                    int cellType = row.nextInt();
                    if(cellType==5){
                        mapCells.put(p, new PacCell(p, cellType));
                    } else {
                        mapCells.put(p, new PacCell(p, 2));
                    }
                    if(cellType>2 && cellType<5){
                        snacks.put(p, new Snack(p, cellType));
                    }
                }
            }
            s.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void connectCells(){
        mapCells.forEach((k, v)->{
            v.setUp(mapCells.get(k.up()));
            v.setDown(mapCells.get(k.down()));
            v.setLeft(mapCells.get(k.left()));
            v.setRight(mapCells.get(k.right()));
            getChildren().add(v);
        });
        snacks.forEach((k, v)->{
            getChildren().add(v);
        });
    }

    private void createLines(){
        mapCells.forEach((k, v)->{
            if(v.getCellType()== CellType.WALL){
                int sides = 0;
                sides+=hasWall(v.up());
                sides+=hasWall(v.down());
                sides+=hasWall(v.left());
                sides+=hasWall(v.right());
                if(sides==3 && (
                        v.up()==null ||
                                v.down()==null ||
                                v.left()==null ||
                                v.right()==null
                )
                ){
                    tryCorner(v);
                }
                if(sides==2 || sides==4){
                    tryCorner(v);
                }
                if(sides>1 && sides<4){
                    tryWallHor(v);
                    tryWallVert(v);
                }
            }
        });
        for (Node l : lines){
            getChildren().add(l);
        }
    }

    private void tryCorner(PacCell v){
        if(v.left()!=null && v.down()!=null){
            if(v.left().isWall() && v.down().isWall()){
                lines.add(new WallCurved(1, v.getCellPoint()));
            }
        }
        if(v.down()!=null && v.right()!=null){
            if(v.down().isWall() && v.right().isWall()){
                lines.add(new WallCurved(2, v.getCellPoint()));
            }
        }
        if(v.right()!=null && v.up()!=null){
            if(v.right().isWall() && v.up().isWall()){
                lines.add(new WallCurved(3, v.getCellPoint()));
            }
        }
        if(v.up()!=null && v.left()!=null){
            if(v.up().isWall() && v.left().isWall()){
                lines.add(new WallCurved(4, v.getCellPoint()));
            }
        }
    }
    private void tryWallHor(PacCell v){
        if(v.up()!=null && v.down()!=null){
            if(v.up().isWall() && v.down().isWall()){
                lines.add(new WallStraight(1, v.getCellPoint()));
            }
        }
    }
    private void tryWallVert(PacCell v){
        if(v.left()!=null && v.right()!=null){
            if(v.left().isWall() && v.right().isWall()){
                lines.add(new WallStraight(2, v.getCellPoint()));
            }
        }
    }

    private int hasWall(PacCell v){
        if(v==null){
            return 0;
        }
        return v.isWall() ? 1:0;
    }

}
