package starb.client.ui.scenes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import starb.client.domain.game.Board;

import java.io.File;

/**
 * A GUI component demonstrating how to use JavaFX GraphicsContext to draw shapes and
 * images.
 */
public class PuzzleUI extends StackPane {

    private static final int WIDTH = 430;
    private static final int HEIGHT = 430;

    private static final File STAR_IMAGE_FILE = new File("Assets/Images/star_black.png");
    private Image starImage;
    private static final File INVALID_STAR_IMAGE_FILE = new File("Assets/Images/star_red.png");
    private Image invalidStarImage;
    private static final File DOT_IMAGE_FILE = new File("Assets/Images/dot_black.png");
    private Image dotImage;

    private Canvas canvas;

    // Grid dimensions and location
    // Must pass in an int only
    private double cellSize = 40;
    private int rows;
    private int cols;
    // Must pass in an int only
    private Point2D gridUpperLeft = new Point2D(15,15);

    private GraphicsContext g;
    private String selectionType;
    private Board board;

    public PuzzleUI(Board newBoard) {
        canvas = new Canvas(WIDTH, HEIGHT);

        selectionType = "star";
        board = newBoard;

        rows = board.getRows();
        cols = board.getColumns();

        // Load the image files
        try {
            starImage = new Image(STAR_IMAGE_FILE.toURI().toURL().toString());
            invalidStarImage = new Image(INVALID_STAR_IMAGE_FILE.toURI().toURL().toString());
            dotImage = new Image(DOT_IMAGE_FILE.toURI().toURL().toString());
        } catch(Exception e) {
            String message = "Unable to load image: " + STAR_IMAGE_FILE;
            System.err.println(message);
            System.err.println(e.getMessage());
            throw new RuntimeException(message);
        }

        this.getChildren().add(canvas);
        canvas.setOnMouseClicked( e -> selectSquare(e));
        drawBoard();
    }

    private void drawBoard() {
        g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);

        // Draw the grid
        g.setLineWidth(1.0);
        g.beginPath();
        for( int i = 0; i < rows + 1; i++ ) {
            double x1 = gridUpperLeft.getX();
            double y1 = gridUpperLeft.getY() + i * cellSize;
            double x2 = gridUpperLeft.getX() + cellSize * cols;
            double y2 = y1;
            g.moveTo(x1, y1);
            g.lineTo(x2, y2);
        }
        for( int i = 0; i < cols + 1; i++ ) {
            double x1 = gridUpperLeft.getX() + i * cellSize;
            double y1 = gridUpperLeft.getY();
            double x2 = x1;
            double y2 = gridUpperLeft.getY() + cellSize * rows;
            g.moveTo( x1, y1 );
            g.lineTo( x2, y2 );
        }
        g.stroke();

        // Draw the section lines of the board
        g.setLineWidth(5.0);
        for (Line line : board.getSectionBoundaries()) {
            g.strokeLine(( (int) line.getStartX() - 1 ) * cellSize + gridUpperLeft.getX(),
                    ( (int) line.getStartY() - 1 ) * cellSize + gridUpperLeft.getY(),
                    ( (int) line.getEndX() - 1 ) * cellSize + gridUpperLeft.getX(),
                    ( (int) line.getEndY() - 1 ) * cellSize + gridUpperLeft.getY());
            // Test
//            System.out.printf("Start: (%d, %d)%n", (int) line.getStartX(), (int) line.getStartY());
//            System.out.printf("Start: (%d, %d)%n%n", (int) line.getEndX(), (int) line.getEndY());
        }
    }

    private void draw(int col, int row) {
        // Update the board square
        if (selectionType.equals("") || selectionType.equals("star") ||
                selectionType.equals("dot")) {
            // TODO - Uncomment when sections are complete in board
            //board.updateSquare(new Point2D(col, row), selectionType);
        }

        // Scale the image and position the image in the center of the square
        double scale = 0.85 * cellSize;
        double positioning = (cellSize - scale) / 2;
        double dotScale = 0.55 * cellSize;
        double dotPositioning = (cellSize - dotScale) / 2;

        // Empty whatever is in the square
        g.clearRect(gridUpperLeft.getX() + (col - 1) * cellSize + positioning,
                gridUpperLeft.getY() + (row - 1) * cellSize + positioning,
                scale, scale
        );

        // Draw based on selectionType
        switch (selectionType) {
            case "star" -> {
                g.drawImage(starImage,
                        gridUpperLeft.getX() + (col - 1) * cellSize + positioning,
                        gridUpperLeft.getY() + (row - 1) * cellSize + positioning,
                        scale, scale
                );
                // Draw the invalid stars to the board
                for (Point2D point : board.getInvalidStars()) {
                    g.drawImage(invalidStarImage,
                            gridUpperLeft.getX() + (point.getX() - 1) * cellSize + positioning,
                            gridUpperLeft.getY() + (point.getY() - 1) * cellSize + positioning,
                            scale, scale
                    );
                }
                // TODO - Redraw the valid stars to the board
                if (board.isComplete()) {
                    completionWindow();
                }
            }
            case "dot" -> {
                g.drawImage(dotImage,
                        gridUpperLeft.getX() + (col - 1) * cellSize + dotPositioning,
                        gridUpperLeft.getY() + (row - 1) * cellSize + dotPositioning,
                        dotScale, dotScale
                );
            }
        }
    }

    private void selectSquare(MouseEvent e) {
        int posX = (int) e.getX();
        int posY = (int) e.getY();

        Point2D location = pixelsToBoardGrid(posX, posY);

        if (location != null) {
            draw((int) location.getX(), (int) location.getY());
        }

    }

    private Point2D pixelsToBoardGrid(int posX, int posY) {
        posX -= (int) gridUpperLeft.getX();
        posY -= (int) gridUpperLeft.getY();

        // Only calculate if inside the grid bounds
        if (posX > 0 && posX < cols * cellSize &&
                posY > 0 && posY < rows * cellSize) {

            // Get the location of the upper left corner of the square
//            posX = ( posX / (int) cellSize ) * (int) cellSize;
//            posY = ( posY / (int) cellSize ) * (int) cellSize;

            // Change to board location
            posX = posX / (int) cellSize + 1;
            posY = posY / (int) cellSize + 1;

            // Test
            // System.out.printf("Click: (%d, %d)%n", posX, posY);
            return new Point2D(posX, posY);

        } else {
            // Test
            // System.out.println("out of bounds");
            return null;
        }
    }

    protected void setSelectionType(String selectionType) {
        this.selectionType = selectionType;
    }

    private void completionWindow() {
        // TODO - implement the completionWindow method
        System.out.println("You win!");
    }
}
