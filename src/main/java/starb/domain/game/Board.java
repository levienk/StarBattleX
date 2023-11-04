package starb.domain.game;

import java.awt.Point;
import javafx.scene.shape.Line;
import org.springframework.data.annotation.Id;

import java.util.*;


public class Board {
    @Id
    private final int id;

    //point is upper left corner of the square
    private HashMap<Point, Square> squares;
    private List<HashMap<Point, Square>> sections;
    private final int ROWS;
    private final int COLUMNS;

    private HashSet<Point> invalidStars;
    private HashSet<Point> validStars;

    private HashSet<Point> solution;

    public Board(int rows, int columns, List<HashMap<Point, Square>> sections, int id) {
        this.id = id;

        ROWS = rows;
        COLUMNS = columns;
        this.sections = sections;

        // Initialize the squares HashMap
        this.squares = new HashMap<>();
        for (HashMap<Point, Square> section : sections) {
            squares.putAll(section);
        }

        // Initialize invalidStars list
        this.invalidStars = new HashSet<>();

        // Initialize validStars list
        this.validStars = new HashSet<>();


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
        for (int i = 1; i <= COLUMNS; i++ ) {
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
        for(int i = 1; i <= ROWS; i++) {
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
        for (Map.Entry<Point, Square> entry: findSection(point).entrySet()) {
            if (entry.getValue().getState().equals("star") || invalidStars.contains(entry.getKey())) {
                starCount++;
                possibleInvalidStars.add(entry.getKey());
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
    private HashMap<Point, Square> findSection(Point point) {
        for (HashMap<Point, Square> section : sections) {
            if (section.containsKey(point)) {
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

                if(newX >= 1 && newX <= COLUMNS && newY >= 1 && newY <= ROWS) {
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
    public List<Line> getSectionBoundaries() {
        ArrayList<Line> allSectionLines = new ArrayList<>();
        for(HashMap<Point, Square> section : sections) {
            allSectionLines.addAll(getSectionBoundary(section));
        }
        return allSectionLines;
    }

    private static List<Line> getSectionBoundary(HashMap<Point, Square> squaresInSection) {
        List<Line> boundaryLines = new ArrayList<>();

        for(Map.Entry<Point, Square> entry : squaresInSection.entrySet()) {
            Point point = entry.getKey();
            Square square = entry.getValue();

            double x = point.getX();
            double y = point.getY();

            // Check top side
            Point squareAbove = new Point((int) x, (int) y - 1);
            if (!squaresInSection.containsKey(squareAbove)) {
                boundaryLines.add(new Line(x, y, x + 1, y));
            }
            // Check right side
            Point squareRight = new Point((int) x + 1, (int) y);
            if (!squaresInSection.containsKey(squareRight)) {
                boundaryLines.add(new Line(x + 1, y, x + 1, y + 1));
            }
            // Check bottom side
            Point squareBelow = new Point((int) x, (int) y + 1);
            if (!squaresInSection.containsKey(squareBelow)) {
                boundaryLines.add(new Line(x, y + 1, x + 1, y + 1));
            }
            // Check left side
            Point squareLeft = new Point((int) x - 1, (int) y);
            if (!squaresInSection.containsKey(squareLeft)) {
                boundaryLines.add(new Line(x, y, x, y + 1));
            }
        }
        return boundaryLines;
    }
    public void clearBoard() {
        for(int i = 1; i <= ROWS; i++) {
            for(int j = 1; j <= COLUMNS; j++) {
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
        return ROWS;
    }
    public int getColumns() {
        return COLUMNS;
    }
    public List<HashMap<Point, Square>> getSections() { return sections;}
    public HashSet<Point> getValidStars() { return validStars;}
    public HashMap<Point, Square> getSquares() { return squares;}
    public HashSet<Point> getInvalidStars() {
        return invalidStars;
    }
    public HashSet<Point> getSolution() { return solution; }

}
