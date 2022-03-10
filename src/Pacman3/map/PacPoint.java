package Pacman3.map;

import javafx.geometry.Rectangle2D;

import java.util.Objects;

/**
 * Was initially a normal class, but the IDE recommended the usage of a java record.
 * The java record will automaticly:
 *      Store input prameters as private final
 *      generate getters for all parameters
 *
 * This class is made to represent a coordinate in the grid. It has simularities of a Point2D, but i figured i make a class of my own to easily make helper functionality.
 *
 * This class is also used as the key for both snack elements and cell elements in hashmaps.
 *
 * @author Krister Iversen
 */
public record PacPoint(int x, int y) {

    public static final double BASE_SCALE = 4;
    public static final double SCALE = 8 * BASE_SCALE;
    public static final double HALF_SCALE = SCALE / 2;
    public static final double THIRD_SCALE = SCALE / 3;
    public static final double FOURTH_SCALE = SCALE / 4;
    public static final double SIXTH_SCALE = SCALE / 6;
    public static final double EIGHT_SCALE = SCALE / 8;

    public static final Rectangle2D BOARD_BOUNDS = new Rectangle2D(0, 0, 23, 30);

    public static final Rectangle2D BOARD_BOUNDS_UPSCALED = new Rectangle2D(0, 0, 23*SCALE, 30*SCALE);

    /**
     * @return Size of one cell X and Y
     */
    public double size() {
        return SCALE;
    }

    /**
     * @return X position in pixels instead of coordinate
     */
    public double pxX() {
        return x * SCALE;
    }

    /**
     * @return Y position in pixels instead of coordinate
     */
    public double pxY() {
        return y * SCALE;
    }

    /**
     * @return Coordinate of cell above current Point. If cell is unreachable returns null
     */
    public PacPoint up() {
        if (y - 1 < BOARD_BOUNDS.getMinY()) {
            return null;
        }
        return new PacPoint(x, y - 1);
    }

    /**
     * @return Coordinate of cell below current Point. If cell is unreachable returns null
     */
    public PacPoint down() {
        if (y + 1 > BOARD_BOUNDS.getMaxY()) {
            return null;
        }
        return new PacPoint(x, y + 1);
    }

    /**
     * @return Coordinate of cell left of current Point. If cell is unreachable returns null
     */
    public PacPoint left() {
        if (x - 1 < BOARD_BOUNDS.getMinX()) {
            return null;
        }
        return new PacPoint(x - 1, y);
    }

    /**
     * @return Coordinate of cell right of current Point. If cell is unreachable returns null
     */
    public PacPoint right() {
        if (x + 1 > BOARD_BOUNDS.getMaxX()) {
            return null;
        }
        return new PacPoint(x + 1, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacPoint cellPoint = (PacPoint) o;
        return x == cellPoint.x && y == cellPoint.y;
    }

    /**
     * @return HashCode used for hashmap lookup.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * @return String of Point coordinates
     */
    @Override
    public String toString() {
        return "Cell: " + x + ", " + y;
    }

    /**
     * Prints toString()
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * Prints toString of current and neighboring nodes.
     */
    public void printAll() {
        System.out.println(
                this + "\n" +
                        "UP: " + up() + "\n" +
                        "DOWN: " + down() + "\n" +
                        "LEFT: " + left() + "\n" +
                        "RIGHT: " + right() + "\n"
        );
    }
}
