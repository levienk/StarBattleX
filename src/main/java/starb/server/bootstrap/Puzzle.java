package starb.server.bootstrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

/**
 * Represents a Star Battle puzzle.  Data is immutable.
 */
@Document("puzzles")
public class Puzzle {

    @Id
    private final String id;
    private final int level;
    private final List<List<Cell>> regions;
    private final List<Cell> solution;
    private final int gridSize;
    private final int numStars;

    @JsonCreator
    public Puzzle(
            @JsonProperty("id") String id,
            @JsonProperty("level") int level,
            @JsonProperty("regions") List<List<Cell>> regions,
            @JsonProperty("solution") List<Cell> solution,
            @JsonProperty("gridSize") int gridSize,
            @JsonProperty("numStars") int numStars ) {
        this.id = id;
        this.level = level;
        this.regions = Collections.unmodifiableList(regions);
        this.solution = Collections.unmodifiableList(solution);
        this.gridSize = gridSize;
        this.numStars = numStars;
    }

    public List<Cell> getSolution() {
        return solution;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public List<List<Cell>> getRegions() {
        return regions;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getNumStars() {
        return numStars;
    }

    @Override
    public String toString() {
        return "Puzzle: size = " + gridSize + " stars = " + numStars + " level = " + level + "\n" +
                regions;
    }
}
