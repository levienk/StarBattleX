package starbattle.client.ui.scenes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import starbattle.client.ui.components.CustomAlert;
import starbattle.client.ui.components.GameEventListener;
import starbattle.domain.DatabaseLoader;
import starbattle.domain.game.Board;
import starbattle.domain.user.User;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

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
    private Point gridUpperLeft = new Point(15,15);
    private double starScale;
    private double dotScale;

    private GraphicsContext g;
    private String selectionType;
    private Board board;

    private static ArrayList<GameEventListener> listeners = new ArrayList<>();

    public PuzzleUI(Board newBoard) {
        canvas = new Canvas(WIDTH, HEIGHT);

        selectionType = "star";
        board = newBoard;

        rows = board.getRows();
        cols = board.getColumns();

        starScale = 0.85 * cellSize;
        dotScale = 0.55 * cellSize;

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
        canvas.setOnMouseClicked( e -> {
            try {
                selectSquare(e);
            } catch (Exception ex) {
                System.out.println("Error loading/updating the user in DatabaseLoader.java");
                throw new RuntimeException(ex);
            }
        });

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

    private void draw(int col, int row) throws Exception {
        // Update the board square
        if (selectionType.equals("") || selectionType.equals("star") ||
                selectionType.equals("dot")) {
            // TODO - Uncomment when board.updateSquare() is functioning
            board.updateSquare(new Point(col, row), selectionType);
        }

        // Scale the image and position the image in the center of the square
        double positioning = (cellSize - starScale) / 2;
        double dotPositioning = (cellSize - dotScale) / 2;

        // Empty whatever is in the square
        g.clearRect(gridUpperLeft.getX() + (col - 1) * cellSize + positioning,
                gridUpperLeft.getY() + (row - 1) * cellSize + positioning,
                starScale, starScale
        );

        // Draw based on selectionType
        switch (selectionType) {
            case "star" -> {
                g.drawImage(starImage,
                        gridUpperLeft.getX() + (col - 1) * cellSize + positioning,
                        gridUpperLeft.getY() + (row - 1) * cellSize + positioning,
                        starScale, starScale
                );

                // Draw the invalid stars to the board
                for (Point point : board.getInvalidStars()) {
                    g.drawImage(invalidStarImage,
                            gridUpperLeft.getX() + (point.getX() - 1) * cellSize + positioning,
                            gridUpperLeft.getY() + (point.getY() - 1) * cellSize + positioning,
                            starScale, starScale
                    );
                }
                // Draw the valid stars to the board
                for (Point point : board.getValidStars()) {
                    g.drawImage(starImage,
                            gridUpperLeft.getX() + (point.getX() - 1) * cellSize + positioning,
                            gridUpperLeft.getY() + (point.getY() - 1) * cellSize + positioning,
                            starScale, starScale
                    );
                }
                if (board.isComplete()) {
                    onCompletion();
                }
            }
            case "dot" -> {
                g.drawImage(dotImage,
                        gridUpperLeft.getX() + (col - 1) * cellSize + dotPositioning,
                        gridUpperLeft.getY() + (row - 1) * cellSize + dotPositioning,
                        dotScale, dotScale
                );
            }
            case "" -> {
                for (Point point : board.getInvalidStars()) {
                    g.drawImage(invalidStarImage,
                            gridUpperLeft.getX() + (point.getX() - 1) * cellSize + positioning,
                            gridUpperLeft.getY() + (point.getY() - 1) * cellSize + positioning,
                            starScale, starScale
                    );
                }
                // Draw the valid stars to the board
                for (Point point : board.getValidStars()) {
                    g.drawImage(starImage,
                            gridUpperLeft.getX() + (point.getX() - 1) * cellSize + positioning,
                            gridUpperLeft.getY() + (point.getY() - 1) * cellSize + positioning,
                            starScale, starScale
                    );
                }
            }
        }
    }

    private void selectSquare(MouseEvent e) throws Exception {
        int posX = (int) e.getX();
        int posY = (int) e.getY();

        Point location = pixelsToBoardGrid(posX, posY);

        if (location != null) {
            draw((int) location.getX(), (int) location.getY());
        }

    }

    private Point pixelsToBoardGrid(int posX, int posY) {
        posX -= (int) gridUpperLeft.getX();
        posY -= (int) gridUpperLeft.getY();

        // Only calculate if inside the grid bounds
        if (posX > 0 && posX < cols * cellSize &&
                posY > 0 && posY < rows * cellSize) {

            // Change to board location
            posX = posX / (int) cellSize + 1;
            posY = posY / (int) cellSize + 1;

            // Test
            // System.out.printf("Click: (%d, %d)%n", posX, posY);
            return new Point(posX, posY);

        } else {
            // Test
            // System.out.println("out of bounds");
            return null;
        }
    }

    protected void setSelectionType(String selectionType) {
        this.selectionType = selectionType;
    }

    private void onCompletion() throws Exception {
        // Update the user
        User user = DatabaseLoader.getUser();

        // Fire all listeners listening to this event.
        puzzleCompleteEvent();

        if (board.getID() == user.getNextPuzzle()) {
            user.updateNextPuzzle();
            // TODO - Update User using event listener here
            DatabaseLoader.updateUser(user);
        }

        // Create the completion window
        CustomAlert alert = new CustomAlert("Congratulations!", "You have completed the level!");
        alert.initOwner(this.getScene().getWindow()); // Set the owner of the alert
        alert.getOkButton().setText("Next Level");
        alert.getOkButton().setOnAction(e -> {
            //next Level Logic
            int nextLevel = board.getID() + 1;

            // If the user has completed all boards and this is the last board, wrap to level 1
            if (user.getNextPuzzle() == -1 &&
                    nextLevel == user.getCompleted().get(user.getCompleted().size())) {
                nextLevel = 1;
            }

            // Open the next level
            try {
                SceneSwitcher.setNewScene(PuzzleScene.class, DatabaseLoader.getBoard(nextLevel),
                                        user.getPlayerRank());
            } catch (Exception ex) {
                System.out.println("Error loading new scene after completion");
                throw new RuntimeException(ex);
            }

            alert.close();
        });
        alert.showAndWait();
        System.out.println("You win!");
    }
    protected void clear() {
        // Get the positioning
        double positioning = (cellSize - starScale) / 2;

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                g.clearRect(gridUpperLeft.getX() + (i) * cellSize + positioning,
                        gridUpperLeft.getY() + (j) * cellSize + positioning,
                        starScale, starScale
                );
            }
        }
        board.clearBoard();
    }

    public static void addGameEventListener(GameEventListener listener) {

        listeners.add(listener);
    }

    private void puzzleCompleteEvent() throws Exception {

        for (GameEventListener listener : listeners) {

            listener.onEvent(GameEventListener.EVENT_TYPE.PUZZLE_SOLVED);
        }
    }
}
