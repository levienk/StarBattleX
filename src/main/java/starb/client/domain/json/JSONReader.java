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
    // Identifier for which puzzle is loaded
    private String puzzleName;

    public JSONReader(String fileName) throws FileNotFoundException {
        // Temporary initialization of the Board
        // Please do not remove until implemented
        board = new Board(10, 10, getTemporarySections(), "Temporary Board");

        // JSON file with a puzzle
        //this.file = new File(fileName);
        // Reads from file
        //this.scnr = new Scanner(this.file);
        // Create a board Object based on the JSON
        /**
         *  Read from a JSON to turn several HashMap<Point2D, Square> objects
         *  into a List
         *  Each line of the JSON will be a Point2D, Square pair.
         */
        //String puzzleName = scnr.nextLine();
        // Assign the Board with a puzzleName in its constructor
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

    // The Board expects sections to cover the entire board
    // TODO - remove this after temporary file and this class is implemented
    private List<HashMap<Point2D, Square>> getTemporarySections() {
        List<HashMap<Point2D, Square>> tempSections = new ArrayList<>();

        // Adds 10 vertical sections to the board
        for (int i = 1; i <= 10; i++) {
            HashMap<Point2D, Square> section = new HashMap<>();
            for (int j = 1; j <= 10; j++) {
                section.put(new Point2D(i, j), new Square());
            }
            tempSections.add(section);
        }
        return tempSections;
    }
}
