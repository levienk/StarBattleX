package starb.server.bootstrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Cell on the game board.
 */
public class Cell {

    private final int row;
    private final int col;

    @JsonCreator
    public Cell(@JsonProperty("row") int row, @JsonProperty("col") int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "[" + row + ", " + col + "]";
    }
}
