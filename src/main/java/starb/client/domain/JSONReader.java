package starb.client.domain;

import javafx.geometry.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    private Board getBoard(){
        // return the Board object
        return this.board;
    }
}
