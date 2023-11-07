package starb.server.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import starb.domain.game.Board;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This component provides an event handler method (onApplicationStart) that is called just after
 * the server is started.  If the database is empty, it loads data from a JSON file and populates
 * the database with that data.
 */
@Component
public class BoardBootstrap {

    private static final String PUZZLE_DATA_FILE = "Assets/Puzzles/puzzles.json";
//    private PuzzleRepository repo;
    // TODO - BoardRepository repo in parameters, this.repo = repo in body
    public BoardBootstrap() {

    }

    /**
     * This method is called automatically, shortly after the server is started.
     * It checks to see if the database is empty, and if so, loads data from the JSON
     * file into the database.  Otherwise, it does nothing.
     *
     * @param event ignored
     */
    @EventListener
    public void onApplicationStart(ApplicationReadyEvent event) {
//        // If the database already has data, do nothing.
//        if( repo.count() > 0 ) return;
//
//        // Load the JSON file, and insert each object into the database
        ObjectMapper mapper = new ObjectMapper();
        File puzzFile = new File(PUZZLE_DATA_FILE);
//        System.out.println("Loading puzzles into DB.");
        try {
            Puzzle[] puzz = mapper.readValue(puzzFile, Puzzle[].class);
            Board[] boards = new Board[puzz.length];
            for (int i = 0; i < puzz.length; i++) {
                // Load the sections for each Board object
                List<List<Point>> sections = new ArrayList<>();
                for (List<Cell> region : puzz[i].getRegions()) {
                    List<Point> section = new ArrayList<>();
                    for (Cell cell : region) {
                        section.add(new Point(cell.getCol() + 1, cell.getRow() + 1));
                    }
                    sections.add(section);
                }

                // Load the solution for each Board object
                HashSet<Point> solution = new HashSet<>();
                for (Cell cell : puzz[i].getSolution()) {
                    solution.add(new Point(cell.getCol() + 1, cell.getRow() + 1));
                }

                boards[i] = new Board(puzz[i].getGridSize(),
                        puzz[i].getGridSize(), sections, solution, puzz[i].getNumStars(),
                        puzz[i].getLevel());
            }
            ObjectMapper boardMapper = new ObjectMapper();
            boardMapper.writeValue(new File("Assets/Boards/boards.json"), boards);
            System.out.println("File Created");
//            repo.saveAll(List.of(puzz));
        } catch(IOException e) {
            System.err.println("Unable to load puzzles: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
