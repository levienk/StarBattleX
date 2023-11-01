package starb.domain.game;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

import java.util.*;


public class Board {
    //point is upper left corner of the square
    private HashMap<Point2D, Square> squares;
    private List<HashMap<Point2D, Square>> sections;
    private final int ROWS;
    private final int COLUMNS;

    private HashSet<Point2D> invalidStars;
    private HashSet<Point2D> validStars;
    private final String PUZZLENAME;
    public Board(int rows, int columns, List<HashMap<Point2D, Square>> sections, String puzzleName) {
        ROWS = rows;
        COLUMNS = columns;
        this.sections = sections;

        // Initialize the squares HashMap
        this.squares = new HashMap<>();
        for (HashMap<Point2D, Square> section : sections) {
            squares.putAll(section);
        }

        //set name
        this.PUZZLENAME = puzzleName;

        // Initialize invalidStars list
        this.invalidStars = new HashSet<>();

        // Initialize validStars list
        this.validStars = new HashSet<>();


    }
    public void updateSquare(Point2D point, String state) {
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
    private boolean checkSquare(Point2D point) {
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
        List<Point2D> possibleInvalidStars = new ArrayList<>();
        for (int i = 1; i <= COLUMNS; i++ ) {
            if(squares.get(new Point2D(i, row)).getState().equals("star") || invalidStars.contains(new Point2D(i, row))) {
                starCount++;
                possibleInvalidStars.add(new Point2D(i, row));
            }
            if (starCount == 2) {
                for (Point2D point : possibleInvalidStars) {
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
        List<Point2D> possibleInvalidStars = new ArrayList<>();
        for(int i = 1; i <= ROWS; i++) {
            if(squares.get(new Point2D(column, i)).getState().equals("star") || invalidStars.contains(new Point2D(column, i))) {
                starCount++;
                possibleInvalidStars.add(new Point2D(column, i));
            }
            if(starCount == 2) {
                for (Point2D point : possibleInvalidStars) {
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
    private boolean checkSection(Point2D point) {
        int starCount = 0;
        boolean starIsInvalid = false;
        List<Point2D> possibleInvalidStars = new ArrayList<>();
        for (Map.Entry<Point2D, Square> entry: findSection(point).entrySet()) {
            if (entry.getValue().getState().equals("star") || invalidStars.contains(entry.getKey())) {
                starCount++;
                possibleInvalidStars.add(entry.getKey());
            }
            if(starCount == 2) {
                for (Point2D point2d : possibleInvalidStars) {
                    if (!invalidStars.contains(point2d)) {
                        invalidStars.add(point2d);
                        validStars.remove(point2d);
                        squares.get(point2d).setState("");
                    }
                }
                starIsInvalid = true;
            }
        }
        return !starIsInvalid;
    }
    private HashMap<Point2D, Square> findSection(Point2D point) {
        for (HashMap<Point2D, Square> section : sections) {
            if (section.containsKey(point)) {
                return section;
            }
        }
        return null;
    }
    private boolean checkArea(Point2D point) {
        boolean starIsInvalid = false;
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                if(i == 0 && j == 0) continue;

                int newX = (int)point.getX() + i;
                int newY = (int)point.getY() + j;

                if(newX >= 1 && newX <= COLUMNS && newY >= 1 && newY <= ROWS) {
                    if (squares.get(new Point2D(newX, newY)).getState().equals("star") || invalidStars.contains(new Point2D(newX, newY))) {
                        if (!invalidStars.contains(new Point2D(newX, newY))) {
                            invalidStars.add(new Point2D(newX, newY));
                            validStars.remove(new Point2D(newX, newY));
                            squares.get(new Point2D(newX, newY)).setState("");
                        }
                        starIsInvalid = true;
                    }
                }
            }
        }
        return !starIsInvalid;
    }
    private void checkInvalidStars() {
        for(Point2D point : invalidStars) {
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
        for (Point2D starPoint : validStars) {
            Square square = squares.get(starPoint);
            if (!square.getState().equals("star")) {
                return false;
            }
        }
        return true;
    }
    public List<Line> getSectionBoundaries() {
        ArrayList<Line> allSectionLines = new ArrayList<>();
        for(HashMap<Point2D, Square> section : sections) {
            allSectionLines.addAll(getSectionBoundary(section));
        }
        return allSectionLines;
    }

    private static List<Line> getSectionBoundary(HashMap<Point2D, Square> squaresInSection) {
        List<Line> boundaryLines = new ArrayList<>();

        for(Map.Entry<Point2D, Square> entry : squaresInSection.entrySet()) {
            Point2D point = entry.getKey();
            Square square = entry.getValue();

            double x = point.getX();
            double y = point.getY();

            // Check top side
            Point2D squareAbove = new Point2D(x, y - 1);
            if (!squaresInSection.containsKey(squareAbove)) {
                boundaryLines.add(new Line(x, y, x + 1, y));
            }
            // Check right side
            Point2D squareRight = new Point2D(x + 1, y);
            if (!squaresInSection.containsKey(squareRight)) {
                boundaryLines.add(new Line(x + 1, y, x + 1, y + 1));
            }
            // Check bottom side
            Point2D squareBelow = new Point2D(x, y + 1);
            if (!squaresInSection.containsKey(squareBelow)) {
                boundaryLines.add(new Line(x, y + 1, x + 1, y + 1));
            }
            // Check left side
            Point2D squareLeft = new Point2D(x - 1, y);
            if (!squaresInSection.containsKey(squareLeft)) {
                boundaryLines.add(new Line(x, y, x, y + 1));
            }
        }
        return boundaryLines;
    }
    public void clearBoard() {
        for(int i = 1; i <= ROWS; i++) {
            for(int j = 1; j <= COLUMNS; j++) {
                squares.get(new Point2D(i, j)).setState("");
                validStars.clear();
                invalidStars.clear();
            }
        }
    }
    public String getPuzzleName() {
        return PUZZLENAME;
    }
    public int getRows() {
        return ROWS;
    }
    public int getColumns() {
        return COLUMNS;
    }
    public List<HashMap<Point2D, Square>> getSections() { return sections;}
    public HashSet<Point2D> getValidStars() { return validStars;}
    public HashMap<Point2D, Square> getSquares() { return squares;}
    public HashSet<Point2D> getInvalidStars() {
        return invalidStars;
    }

}
