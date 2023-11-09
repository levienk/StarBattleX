package starb.domain.json;

import starb.domain.game.Board;
import starb.domain.game.Square;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class JSONReader {
    // The puzzle board loaded from the JSON
    private Board board;
    // The JSON file with the puzzle data
    private File file;
    // Scanner to read from the file
    private Scanner scanner;
    // Identifier for which puzzle is loaded
    private String puzzleName;

    public JSONReader(String fileName) throws FileNotFoundException {
        // Temporary initialization of the Board
        // Please do not remove until implemented
        List<Point> solution = new ArrayList<>();
        board = new Board(10, 10, getTemporarySections(), solution, 2, 1);

        // JSON file with a puzzle
        //this.file = new File(fileName);
        // Reads from file
        //this.scanner = new Scanner(this.file);
        // Create a board Object based on the JSON
        /*
           Read from a JSON to turn several HashMap<Point, Square> objects
           into a List
           Each line of the JSON will be a Point, Square pair.
         */
        //String puzzleName = scanner.nextLine();
        // Assign the Board with a puzzleName in its constructor
    }

    private List<Map<Point, Square>> getSections(){ // Called by the constructor
        // Implement a way to get the sections from the JSON File
        // Read the JSON file
        // Produce a List to hold the sections
        // Produce a Map<Point, Square> for each section
        // Return the List of sections
        return null;
    }

    public Board getBoard(){
        // return the Board object
        return this.board;
    }

    // The Board expects sections to cover the entire board
    // TODO - remove this after temporary file and this class is implemented
    private List<List<Point>> getTemporarySections() {
        List<List<Point>> tempSections = new ArrayList<>();

        // Adds 10 vertical sections to the board
        for (int i = 1; i <= 10; i++) {
            List<Point> section = new ArrayList<>();
            for (int j = 1; j <= 10; j++) {
                section.add(new Point(i, j));
            }
            tempSections.add(section);
        }
        return tempSections;
    }
}
