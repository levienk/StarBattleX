package starb.domain.game;

import java.awt.Point;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.shape.Line;
import org.apache.tomcat.util.net.TLSClientHelloExtractor;
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

        this.rows = rows;
        this.columns = columns;
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
        Square square = squares.get(point);
        //update square object based on state
        switch(state) {
            case "star":
                //if square is already a star do nothing
               if(square.getState().equals("star")) {
                   return;
               }
               //make the square a star
               square.setState("star");

               //if star is valid, set star validity to true
                if(checkSquare(point)) {
                    square.setStarValidity(true);
                }
                break;
            case "dot":
                //set square state to dot
                square.setState("dot");

                //set square star validity to false
                square.setStarValidity(false);
                break;
            case "":
                //set square state to blank
                square.setState("");

                //set square star validity to false
                square.setStarValidity(false);
                break;
            default:
                return;
        }
        //update lists
        updateStarSets(point);

        //recheck invalid and valid stars
        checkValidStars();
        checkInvalidStars();

    }

    private boolean checkSquare(Point point) {
        if (checkArea(point) && checkSection(point) && checkColumn((int) point.getX()) && checkRow((int) point.getY())) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkRow(int row) {
        int starCount = 0;
        for (int i = 1; i <= columns; i++) {
            if (squares.get(new Point(i, row)).getState().equals("star")) {
                starCount++;
            }
            if (starCount > 2) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(int column) {
        int starCount = 0;

        for(int i = 1; i <= rows; i++) {
            if(squares.get(new Point(column, i)).getState().equals("star")) {
                starCount++;
            }
            if(starCount > 2) {
               return false;
            }
        }
        return true;
    }

    private boolean checkSection(Point point) {
        int starCount = 0;

        for (Point pointInSection : findSection(point)) {
            if (squares.get(pointInSection).getState().equals("star")) {
                starCount++;
            }
            if(starCount > 2) {
               return false;
            }
        }
        return true;
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

        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                if(i == 0 && j == 0) continue;

                int newX = (int)point.getX() + i;
                int newY = (int)point.getY() + j;

                if(newX >= 1 && newX <= columns && newY >= 1 && newY <= rows) {
                    if (squares.get(new Point(newX, newY)).getState().equals("star")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void checkInvalidStars() {
        Iterator<Point> iterator = invalidStars.iterator();
        while(iterator.hasNext()) {
            Point point = iterator.next();
            if(checkSquare(point)){
                validStars.add(point);
                iterator.remove(); // Safe removal

                squares.get(point).setStarValidity(true);
            }
        }
    }
    private void checkValidStars() {
        Iterator<Point> iterator = validStars.iterator();
        while (iterator.hasNext()) {
            Point point = iterator.next();
            if (!checkSquare(point)) {
                invalidStars.add(point);
                iterator.remove(); // Safe removal

                squares.get(point).setStarValidity(false);
            }
        }
    }



    @Transient
    @JsonIgnore
    public boolean isComplete() {
        if(validStars.size() != 20) {
            return false;
        }
        for (Point starPoint : solution) {
            if (!validStars.contains(starPoint)) {
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
    public void updateStarSets(Point point) {
        Square square = squares.get(point);
        //if square state is star
        if(square.getState().equals("star")) {
            //if square is a valid star
            if(square.getStarValidity()) {
                validStars.add(point);
                invalidStars.remove(point);
            }
            //if square is a invalid star
            else {
                invalidStars.add(point);
                validStars.remove(point);
            }
        }
        //if it is not a star
        else {
            validStars.remove(point);
            invalidStars.remove(point);
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
