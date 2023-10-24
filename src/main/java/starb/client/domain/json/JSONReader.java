package starb.client.domain.json;

import javafx.geometry.Point2D;
import starb.client.domain.game.Board;
import starb.client.domain.game.Square;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class JSONReader {
    // The puzzle board loaded from the JSON
    private Board board;
    // The JSON file with the puzzle data
    private File file;
    // Scanner to read from the file
    private Scanner scnr;
    // EventListener to push data to the UI
    private EventListener eventListener;
    // Identifier for which puzzle is loaded
    private String puzzleName;

    public JSONReader(String fileName) throws FileNotFoundException {
        // Temporary initialization of the Board
        // Please do not remove until implemented
        List<HashMap<Point2D, Square>> sections = new ArrayList<>();
        HashMap<Point2D, Square> section = new HashMap<>();
        section.put(new Point2D(2,3), new Square());
        sections.add(section);
        // The Board expects sections to be filled, so this will cause problems
        board = new Board(10, 10, sections, "Temporary Board");

        // JSON file with a puzzle
        this.file = new File(fileName);
        // Reads from file
        this.scnr = new Scanner(this.file);
        // Create a board Object based on the JSON
        // Assign the Board with a puzzleName in its constructor
        // EventListener may be redundant.
    }

    private List<Map<Point2D, Square>> getSections(){ // Called by the constructor
        // Implement a way to get the sections from the JSON File
        // Read the JSON file
        // Produce a List to hold the sections
        // Produce a Map<Point2D, Square> for each section
        // Return the List of sections
        return null;
    }

    public Board getBoard(){
        // return the Board object
        return this.board;
    }
}
