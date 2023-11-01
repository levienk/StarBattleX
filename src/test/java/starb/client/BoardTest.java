package starb.client;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import starb.domain.game.Board;
import starb.domain.game.Square;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoardTest {
    //Todo: update tests to fit new row and column starting indexes
    @Test
    public void testBoardInitialization() {
        Board myBoard = initalizeBoard();

        assertEquals(10, myBoard.getRows());
        assertEquals(10, myBoard.getColumns());
    }
    @Test
    public void testStarPlacementRules() {
        Board board = initalizeBoard();

        Point2D validStarPoint = new Point2D(2, 2);
        board.updateSquare(validStarPoint, "star");

        Point2D adjacentStarPoint = new Point2D(2, 3);
        board.updateSquare(adjacentStarPoint, "star");

        assertTrue(board.getInvalidStars().contains(adjacentStarPoint));
    }
    @Test
    public void testAllCoordinatesCovered() {
        Board myBoard = initalizeBoard();

        boolean[][] coordinatesCovered = new boolean[10][10];

        for (HashMap<Point2D, Square> section : myBoard.getSections()) {
            for (Point2D point : section.keySet()) {
                int x = (int) point.getX();
                int y = (int) point.getY();
                assertFalse(coordinatesCovered[x][y]);
                coordinatesCovered[x][y] = true;
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(coordinatesCovered[i][j]);
            }
        }
    }
    @Test
    public void testUpdateSquareToStarState() {
        Board board = initalizeBoard();
        Point2D point = new Point2D(0, 0);
        board.updateSquare(point, "star");
        assertEquals("star", board.getSections().get(0).get(point).getState());
    }

    @Test
    public void basicTest() {
        Board board = initalizeBoard();

        board.updateSquare(new Point2D(1, 0), "star");
        board.updateSquare(new Point2D(4, 0), "star");
        board.updateSquare(new Point2D(7, 1), "star");

        System.out.println(board.getSquares().get(new Point2D(1, 0)).getState());
        System.out.println(board.getSquares().get(new Point2D(4, 0)).getState());
        System.out.println(board.getSquares().get(new Point2D(7, 1)).getState());












        //assertTrue(board.getSquares().get(new Point2D(2, 0)).getState().equals("star"));



    }
    @Test
    public void testInvalidStarPlacement() {
        Board board = initalizeBoard();
        Point2D point1 = new Point2D(0, 0);
        Point2D point2 = new Point2D(0, 2);
        Point2D point3 = new Point2D(0, 4);
        board.updateSquare(point1, "star");
        board.updateSquare(point2, "star");
        board.updateSquare(point3, "star");
        assertTrue(board.getInvalidStars().contains(point3));
    }
    @Test
    public void testIsCompleteWithValidStars() {
        Board board = initalizeBoard();

        board.updateSquare(new Point2D(1, 0), "star");
        board.updateSquare(new Point2D(4, 0), "star");

        board.updateSquare(new Point2D(6, 1), "star");
        board.updateSquare(new Point2D(8, 1), "star");

        board.updateSquare(new Point2D(1, 2), "star");
        board.updateSquare(new Point2D(3, 2), "star");

        board.updateSquare(new Point2D(7, 3), "star");
        board.updateSquare(new Point2D(9, 3), "star");

        board.updateSquare(new Point2D(3, 4), "star");
        board.updateSquare(new Point2D(5, 4), "star");

        board.updateSquare(new Point2D(0, 5), "star");
        board.updateSquare(new Point2D(7, 5), "star");

        board.updateSquare(new Point2D(2, 6), "star");
        board.updateSquare(new Point2D(4, 6), "star");

        board.updateSquare(new Point2D(6, 7), "star");
        board.updateSquare(new Point2D(8, 7), "star");

        board.updateSquare(new Point2D(0, 8), "star");
        board.updateSquare(new Point2D(2, 8), "star");

        board.updateSquare(new Point2D(5, 9), "star");
        board.updateSquare(new Point2D(9, 9), "star");

        assertTrue(board.isComplete());
    }
    @Test
    public void testSectionBoundaryRetrieval() {
        Board board = initalizeBoard();
        assertNotNull(board.getSectionBoundaries());
    }
    private Board initalizeBoard() {
        List<HashMap<Point2D, Square>> sections = new ArrayList();

        //section 1
        HashMap<Point2D, Square> section1 = new HashMap<>();
        section1.put(new Point2D(0, 0), new Square());
        section1.put(new Point2D(1, 0), new Square());
        section1.put(new Point2D(2, 0), new Square());
        section1.put(new Point2D(3, 0), new Square());
        section1.put(new Point2D(4, 0), new Square());
        section1.put(new Point2D(5, 0), new Square());
        section1.put(new Point2D(6, 0), new Square());
        section1.put(new Point2D(7, 0), new Square());
        section1.put(new Point2D(0, 1), new Square());
        section1.put(new Point2D(1, 1), new Square());
        section1.put(new Point2D(2, 1), new Square());
        section1.put(new Point2D(3, 1), new Square());
        section1.put(new Point2D(4, 1), new Square());
        section1.put(new Point2D(5, 1), new Square());
        section1.put(new Point2D(7, 1), new Square());
        section1.put(new Point2D(4, 2), new Square());
        sections.add(section1);

        //section 2
        HashMap<Point2D, Square> section2 = new HashMap<>();
        section2.put(new Point2D(8, 0), new Square());
        section2.put(new Point2D(8, 1), new Square());
        section2.put(new Point2D(8, 2), new Square());
        section2.put(new Point2D(8, 3), new Square());
        section2.put(new Point2D(8, 4), new Square());
        section2.put(new Point2D(8, 5), new Square());
        section2.put(new Point2D(9, 0), new Square());
        section2.put(new Point2D(9, 1), new Square());
        section2.put(new Point2D(9, 2), new Square());
        section2.put(new Point2D(9, 3), new Square());
        section2.put(new Point2D(9, 4), new Square());
        section2.put(new Point2D(9, 5), new Square());
        section2.put(new Point2D(9, 6), new Square());
        section2.put(new Point2D(9, 7), new Square());
        sections.add(section2);

        //section 3
        HashMap<Point2D, Square> section3 = new HashMap<>();
        section3.put(new Point2D(0, 3), new Square());
        section3.put(new Point2D(0, 4), new Square());
        section3.put(new Point2D(0, 5), new Square());
        section3.put(new Point2D(0, 6), new Square());
        section3.put(new Point2D(0, 2), new Square());
        section3.put(new Point2D(0, 7), new Square());
        section3.put(new Point2D(0, 8), new Square());

        section3.put(new Point2D(1, 3), new Square());
        section3.put(new Point2D(1, 4), new Square());
        section3.put(new Point2D(1, 5), new Square());
        section3.put(new Point2D(1, 6), new Square());
        section3.put(new Point2D(1, 7), new Square());

        section3.put(new Point2D(2, 3), new Square());
        section3.put(new Point2D(3, 3), new Square());
        section3.put(new Point2D(2, 4), new Square());

        section3.put(new Point2D(2, 7), new Square());
        section3.put(new Point2D(3, 7), new Square());
        section3.put(new Point2D(4, 7), new Square());
        section3.put(new Point2D(5, 7), new Square());
        sections.add(section3);

        //section 4
        HashMap<Point2D, Square> section4 = new HashMap<>();
        section4.put(new Point2D(1, 2), new Square());
        section4.put(new Point2D(2, 2), new Square());
        section4.put(new Point2D(3, 2), new Square());
        sections.add(section4);

        //section 5
        HashMap<Point2D, Square> section5 = new HashMap<>();
        section5.put(new Point2D(5, 2), new Square());
        section5.put(new Point2D(6, 2), new Square());
        section5.put(new Point2D(7, 2), new Square());

        section5.put(new Point2D(6, 1), new Square());
        section5.put(new Point2D(7, 3), new Square());
        sections.add(section5);

        //section 6
        HashMap<Point2D, Square> section6 = new HashMap<>();
        section6.put(new Point2D(4, 3), new Square());

        section6.put(new Point2D(3, 4), new Square());
        section6.put(new Point2D(4, 4), new Square());
        section6.put(new Point2D(5, 4), new Square());

        section6.put(new Point2D(3, 5), new Square());
        section6.put(new Point2D(4, 5), new Square());
        section6.put(new Point2D(5, 5), new Square());
        sections.add(section6);

        //section 7
        HashMap<Point2D, Square> section7 = new HashMap<>();
        section7.put(new Point2D(5, 3), new Square());
        section7.put(new Point2D(5, 6), new Square());

        section7.put(new Point2D(6, 3), new Square());
        section7.put(new Point2D(6, 4), new Square());
        section7.put(new Point2D(6, 5), new Square());
        section7.put(new Point2D(6, 6), new Square());
        section7.put(new Point2D(6, 7), new Square());

        section7.put(new Point2D(7, 4), new Square());
        section7.put(new Point2D(7, 5), new Square());
        section7.put(new Point2D(7, 6), new Square());
        section7.put(new Point2D(7, 7), new Square());
        sections.add(section7);

        //section 8
        HashMap<Point2D, Square> section8 = new HashMap<>();
        section8.put(new Point2D(8, 6), new Square());
        section8.put(new Point2D(8, 7), new Square());
        section8.put(new Point2D(8, 8), new Square());

        section8.put(new Point2D(9, 8), new Square());
        section8.put(new Point2D(9, 9), new Square());
        sections.add(section8);

        //section 9
        HashMap<Point2D, Square> section9 = new HashMap<>();

        section9.put(new Point2D(2, 5), new Square());

        section9.put(new Point2D(2, 6), new Square());
        section9.put(new Point2D(3, 6), new Square());
        section9.put(new Point2D(4, 6), new Square());
        sections.add(section9);

        //section 10
        HashMap<Point2D, Square> section10 = new HashMap<>();

        section10.put(new Point2D(1,8), new Square());
        section10.put(new Point2D(2,8), new Square());
        section10.put(new Point2D(3,8), new Square());
        section10.put(new Point2D(4,8), new Square());
        section10.put(new Point2D(5,8), new Square());
        section10.put(new Point2D(6,8), new Square());
        section10.put(new Point2D(7,8), new Square());

        section10.put(new Point2D(0,9), new Square());
        section10.put(new Point2D(1,9), new Square());
        section10.put(new Point2D(2,9), new Square());
        section10.put(new Point2D(3,9), new Square());
        section10.put(new Point2D(4,9), new Square());
        section10.put(new Point2D(5,9), new Square());
        section10.put(new Point2D(6,9), new Square());
        section10.put(new Point2D(7,9), new Square());
        section10.put(new Point2D(8,9), new Square());

        sections.add(section10);

        return new Board(10, 10, sections, "myBookie");
    }



}
