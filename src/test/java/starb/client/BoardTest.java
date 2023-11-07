package starb.client;

import org.junit.jupiter.api.Test;
import starb.domain.game.Board;
import starb.domain.game.Square;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

        Point validStarPoint = new Point(2, 2);
        board.updateSquare(validStarPoint, "star");

        Point adjacentStarPoint = new Point(2, 3);
        board.updateSquare(adjacentStarPoint, "star");

        assertTrue(board.getInvalidStars().contains(adjacentStarPoint));
    }
    @Test
    public void testAllCoordinatesCovered() {
        Board myBoard = initalizeBoard();

        boolean[][] coordinatesCovered = new boolean[10][10];

        for (List<Point> section : myBoard.getSections()) {
            for (Point point : section) {
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
        Point point = new Point(0, 0);
        board.updateSquare(point, "star");
        assertEquals("star", board.getSquares().get(point).getState());
    }

    @Test
    public void basicTest() {
        Board board = initalizeBoard();

        board.updateSquare(new Point(1, 0), "star");
        board.updateSquare(new Point(4, 0), "star");
        board.updateSquare(new Point(7, 1), "star");

        System.out.println(board.getSquares().get(new Point(1, 0)).getState());
        System.out.println(board.getSquares().get(new Point(4, 0)).getState());
        System.out.println(board.getSquares().get(new Point(7, 1)).getState());












        //assertTrue(board.getSquares().get(new Point(2, 0)).getState().equals("star"));



    }
    @Test
    public void testInvalidStarPlacement() {
        Board board = initalizeBoard();
        Point point1 = new Point(0, 0);
        Point point2 = new Point(0, 2);
        Point point3 = new Point(0, 4);
        board.updateSquare(point1, "star");
        board.updateSquare(point2, "star");
        board.updateSquare(point3, "star");
        assertTrue(board.getInvalidStars().contains(point3));
    }
    @Test
    public void testIsCompleteWithValidStars() {
        Board board = initalizeBoard();

        board.updateSquare(new Point(1, 0), "star");
        board.updateSquare(new Point(4, 0), "star");

        board.updateSquare(new Point(6, 1), "star");
        board.updateSquare(new Point(8, 1), "star");

        board.updateSquare(new Point(1, 2), "star");
        board.updateSquare(new Point(3, 2), "star");

        board.updateSquare(new Point(7, 3), "star");
        board.updateSquare(new Point(9, 3), "star");

        board.updateSquare(new Point(3, 4), "star");
        board.updateSquare(new Point(5, 4), "star");

        board.updateSquare(new Point(0, 5), "star");
        board.updateSquare(new Point(7, 5), "star");

        board.updateSquare(new Point(2, 6), "star");
        board.updateSquare(new Point(4, 6), "star");

        board.updateSquare(new Point(6, 7), "star");
        board.updateSquare(new Point(8, 7), "star");

        board.updateSquare(new Point(0, 8), "star");
        board.updateSquare(new Point(2, 8), "star");

        board.updateSquare(new Point(5, 9), "star");
        board.updateSquare(new Point(9, 9), "star");

        assertTrue(board.isComplete());
    }
    @Test
    public void testSectionBoundaryRetrieval() {
        Board board = initalizeBoard();
        assertNotNull(board.getSectionBoundaries());
    }
    private Board initalizeBoard() {
        List<List<Point>> sections = new ArrayList();

        //section 1
        List<Point> section1 = new ArrayList<>();
        section1.add(new Point(0, 0));
        section1.add(new Point(1, 0));
        section1.add(new Point(2, 0));
        section1.add(new Point(3, 0));
        section1.add(new Point(4, 0));
        section1.add(new Point(5, 0));
        section1.add(new Point(6, 0));
        section1.add(new Point(7, 0));
        section1.add(new Point(0, 1));
        section1.add(new Point(1, 1));
        section1.add(new Point(2, 1));
        section1.add(new Point(3, 1));
        section1.add(new Point(4, 1));
        section1.add(new Point(5, 1));
        section1.add(new Point(7, 1));
        section1.add(new Point(4, 2));
        sections.add(section1);

        //section 2
        List<Point> section2 = new ArrayList<>();
        section2.add(new Point(8, 0));
        section2.add(new Point(8, 1));
        section2.add(new Point(8, 2));
        section2.add(new Point(8, 3));
        section2.add(new Point(8, 4));
        section2.add(new Point(8, 5));
        section2.add(new Point(9, 0));
        section2.add(new Point(9, 1));
        section2.add(new Point(9, 2));
        section2.add(new Point(9, 3));
        section2.add(new Point(9, 4));
        section2.add(new Point(9, 5));
        section2.add(new Point(9, 6));
        section2.add(new Point(9, 7));
        sections.add(section2);

        //section 3
        List<Point> section3 = new ArrayList<>();
        section3.add(new Point(0, 3));
        section3.add(new Point(0, 4));
        section3.add(new Point(0, 5));
        section3.add(new Point(0, 6));
        section3.add(new Point(0, 2));
        section3.add(new Point(0, 7));
        section3.add(new Point(0, 8));

        section3.add(new Point(1, 3));
        section3.add(new Point(1, 4));
        section3.add(new Point(1, 5));
        section3.add(new Point(1, 6));
        section3.add(new Point(1, 7));

        section3.add(new Point(2, 3));
        section3.add(new Point(3, 3));
        section3.add(new Point(2, 4));

        section3.add(new Point(2, 7));
        section3.add(new Point(3, 7));
        section3.add(new Point(4, 7));
        section3.add(new Point(5, 7));
        sections.add(section3);

        //section 4
        List<Point> section4 = new ArrayList<>();
        section4.add(new Point(1, 2));
        section4.add(new Point(2, 2));
        section4.add(new Point(3, 2));
        sections.add(section4);

        //section 5
        List<Point> section5 = new ArrayList<>();
        section5.add(new Point(5, 2));
        section5.add(new Point(6, 2));
        section5.add(new Point(7, 2));

        section5.add(new Point(6, 1));
        section5.add(new Point(7, 3));
        sections.add(section5);

        //section 6
        List<Point> section6 = new ArrayList<>();
        section6.add(new Point(4, 3));

        section6.add(new Point(3, 4));
        section6.add(new Point(4, 4));
        section6.add(new Point(5, 4));

        section6.add(new Point(3, 5));
        section6.add(new Point(4, 5));
        section6.add(new Point(5, 5));
        sections.add(section6);

        //section 7
        List<Point> section7 = new ArrayList<>();
        section7.add(new Point(5, 3));
        section7.add(new Point(5, 6));

        section7.add(new Point(6, 3));
        section7.add(new Point(6, 4));
        section7.add(new Point(6, 5));
        section7.add(new Point(6, 6));
        section7.add(new Point(6, 7));

        section7.add(new Point(7, 4));
        section7.add(new Point(7, 5));
        section7.add(new Point(7, 6));
        section7.add(new Point(7, 7));
        sections.add(section7);

        //section 8
        List<Point> section8 = new ArrayList<>();
        section8.add(new Point(8, 6));
        section8.add(new Point(8, 7));
        section8.add(new Point(8, 8));

        section8.add(new Point(9, 8));
        section8.add(new Point(9, 9));
        sections.add(section8);

        //section 9
        List<Point> section9 = new ArrayList<>();

        section9.add(new Point(2, 5));

        section9.add(new Point(2, 6));
        section9.add(new Point(3, 6));
        section9.add(new Point(4, 6));
        sections.add(section9);

        //section 10
        List<Point> section10 = new ArrayList<>();

        section10.add(new Point(1,8));
        section10.add(new Point(2,8));
        section10.add(new Point(3,8));
        section10.add(new Point(4,8));
        section10.add(new Point(5,8));
        section10.add(new Point(6,8));
        section10.add(new Point(7,8));

        section10.add(new Point(0,9));
        section10.add(new Point(1,9));
        section10.add(new Point(2,9));
        section10.add(new Point(3,9));
        section10.add(new Point(4,9));
        section10.add(new Point(5,9));
        section10.add(new Point(6,9));
        section10.add(new Point(7,9));
        section10.add(new Point(8,9));

        sections.add(section10);

        return new Board(10, 10, sections, new HashSet<Point>(), 2, 0);
    }



}
