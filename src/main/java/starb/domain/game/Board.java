package starb.domain.game;

import java.awt.Point;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.shape.Line;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document("boards")
public class Board {
    @Id
    private final int id;

    //point is upper left corner of the square
    @Transient
    @JsonIgnore
    private HashMap<Point, Square> squares;
    private List<List<Point>> sections;
    private int rows;
    private int columns;

    @Transient
    @JsonIgnore
    private HashSet<Point> invalidStars;
    @Transient
    @JsonIgnore
    private HashSet<Point> validStars;

    private List<Point> solution;

    private final int numStars;

    @JsonCreator
    public Board(@JsonProperty("rows") int rows,
                 @JsonProperty("columns") int columns,
                 @JsonProperty("sections") List<List<Point>> sections,
                 @JsonProperty("solution") List<Point> solution,
                 @JsonProperty("numStars") int numStars,
                 @JsonProperty("id") int id) {
        this.id = id;

        rows = rows;
        columns = columns;
        this.sections = sections;

        initializeSquares();

        // Initialize invalidStars list
        this.invalidStars = new HashSet<>();

        // Initialize validStars list
        this.validStars = new HashSet<>();

        // Initialize solution list
        this.solution = solution;

        // Number of stars per section, row, and column
        this.numStars = numStars;

    }

    private void initializeSquares() {
        // Initialize the squares HashMap
        this.squares = new HashMap<>();
        for (List<Point> section : sections) {
            for (Point point : section) {
                squares.put(point, new Square());
            }
        }
    }

    public void updateSquare(Point point, String state) {
        if(state.equals("star") && checkSquare(point) && !(squares.get(point).getState().equals("star"))) {
            squares.get(point).setState("star");
            if(invalidStars.contains(point)) {
                invalidStars.remove(point);
            }
            validStars.add(point);

        } else if ( state.equals("dot") ) {
            squares.get(point).setState("dot");
            //remove from both lists
            if(validStars.contains(point)) {
                validStars.remove(point);
            }
            if(invalidStars.contains(point)) {
                invalidStars.remove(point);
            }
        } else if (state.equals("")) {

            validStars.remove(point);
            squares.get(point).setState("");
            //remove from both lists
            if(validStars.contains(point)) {
                validStars.remove(point);
            }
            if(invalidStars.contains(point)) {
                invalidStars.remove(point);
            }
            //todo: affected stars are not being updated properly after star is removed, once the method below works, uncomment
            //checkInvalidStars();
        }
    }

    private boolean checkSquare(Point point) {
        if (checkArea(point) && checkSection(point) && checkColumn((int) point.getX()) && checkRow((int) point.getY())) {
            System.out.println("real!");
            return true;
        }
        else {
            if(validStars.contains(point)) {
                validStars.remove(point);
            }
            invalidStars.add(point);
            return false;
        }
    }

    private boolean checkRow(int row) {
        int starCount = 0;
        boolean starIsInvalid = false;
        List<Point> possibleInvalidStars = new ArrayList<>();
        for (int i = 1; i <= columns; i++ ) {
            if(squares.get(new Point(i, row)).getState().equals("star") || invalidStars.contains(new Point(i, row))) {
                starCount++;
                possibleInvalidStars.add(new Point(i, row));
            }
            if (starCount == 2) {
                for (Point point : possibleInvalidStars) {
                    if (!invalidStars.contains(point)) {
                        invalidStars.add(point);
                        validStars.remove(point);
                        squares.get(point).setState("");
                    }
                }
                starIsInvalid = true;
            }
        }
        return !starIsInvalid;
    }

    private boolean checkColumn(int column) {
        int starCount = 0;
        boolean starIsInvalid = false;
        List<Point> possibleInvalidStars = new ArrayList<>();
        for(int i = 1; i <= rows; i++) {
            if(squares.get(new Point(column, i)).getState().equals("star") || invalidStars.contains(new Point(column, i))) {
                starCount++;
                possibleInvalidStars.add(new Point(column, i));
            }
            if(starCount == 2) {
                for (Point point : possibleInvalidStars) {
                    if (!invalidStars.contains(point)) {
                        invalidStars.add(point);
                        validStars.remove(point);
                        squares.get(point).setState("");
                    }
                }
                starIsInvalid = true;
            }
        }
        return !starIsInvalid;

    }

    private boolean checkSection(Point point) {
        int starCount = 0;
        boolean starIsInvalid = false;
        List<Point> possibleInvalidStars = new ArrayList<>();
        for (Point pointInSection : findSection(point)) {
            if (squares.get(pointInSection).getState().equals("star") ||
                    invalidStars.contains(pointInSection)) {
                starCount++;
                possibleInvalidStars.add(pointInSection);
            }
            if(starCount == 2) {
                for (Point Point : possibleInvalidStars) {
                    if (!invalidStars.contains(Point)) {
                        invalidStars.add(Point);
                        validStars.remove(Point);
                        squares.get(Point).setState("");
                    }
                }
                starIsInvalid = true;
            }
        }
        return !starIsInvalid;
    }

    private List<Point> findSection(Point point) {
        for (List<Point> section : sections) {
            if (section.contains(point)) {
                return section;
            }
        }
        return null;
    }

    private boolean checkArea(Point point) {
        boolean starIsInvalid = false;
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                if(i == 0 && j == 0) continue;

                int newX = (int)point.getX() + i;
                int newY = (int)point.getY() + j;

                if(newX >= 1 && newX <= columns && newY >= 1 && newY <= rows) {
                    if (squares.get(new Point(newX, newY)).getState().equals("star") || invalidStars.contains(new Point(newX, newY))) {
                        if (!invalidStars.contains(new Point(newX, newY))) {
                            invalidStars.add(new Point(newX, newY));
                            validStars.remove(new Point(newX, newY));
                            squares.get(new Point(newX, newY)).setState("");
                        }
                        starIsInvalid = true;
                    }
                }
            }
        }
        return !starIsInvalid;
    }

    private void checkInvalidStars() {
        for(Point point : invalidStars) {
            if(checkSquare(point)) {
                invalidStars.remove(point);
                validStars.add(point);
                squares.get(point).setState("star");
            }
        }
    }

    @Transient
    @JsonIgnore
    public boolean isComplete() {
        if(validStars.size() != 20) {
            return false;
        }
        for (Point starPoint : validStars) {
            Square square = squares.get(starPoint);
            if (!square.getState().equals("star")) {
                return false;
            }
        }
        return true;
    }

    @Transient
    @JsonIgnore
    public List<Line> getSectionBoundaries() {
        ArrayList<Line> allSectionLines = new ArrayList<>();
        for(List<Point> section : sections) {
            allSectionLines.addAll(getSectionBoundary(section));
        }
        return allSectionLines;
    }

    private static List<Line> getSectionBoundary(List<Point> pointsInSection) {
        List<Line> boundaryLines = new ArrayList<>();

        for(Point point : pointsInSection) {

            double x = point.getX();
            double y = point.getY();

            // Check top side
            Point squareAbove = new Point((int) x, (int) y - 1);
            if (!pointsInSection.contains(squareAbove)) {
                boundaryLines.add(new Line(x, y, x + 1, y));
            }
            // Check right side
            Point squareRight = new Point((int) x + 1, (int) y);
            if (!pointsInSection.contains(squareRight)) {
                boundaryLines.add(new Line(x + 1, y, x + 1, y + 1));
            }
            // Check bottom side
            Point squareBelow = new Point((int) x, (int) y + 1);
            if (!pointsInSection.contains(squareBelow)) {
                boundaryLines.add(new Line(x, y + 1, x + 1, y + 1));
            }
            // Check left side
            Point squareLeft = new Point((int) x - 1, (int) y);
            if (!pointsInSection.contains(squareLeft)) {
                boundaryLines.add(new Line(x, y, x, y + 1));
            }
        }
        return boundaryLines;
    }

    public void clearBoard() {
        for(int i = 1; i <= rows; i++) {
            for(int j = 1; j <= columns; j++) {
                squares.get(new Point(i, j)).setState("");
                validStars.clear();
                invalidStars.clear();
            }
        }
    }

    public int getID() {
        return id;
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
    public List<List<Point>> getSections() { return sections;}
    @Transient
    @JsonIgnore
    public HashSet<Point> getValidStars() { return validStars;}
    @Transient
    @JsonIgnore
    public HashMap<Point, Square> getSquares() { return squares;}
    @Transient
    @JsonIgnore
    public HashSet<Point> getInvalidStars() {
        return invalidStars;
    }
    public List<Point> getSolution() { return solution; }
    public int getNumStars() { return numStars; }

}
