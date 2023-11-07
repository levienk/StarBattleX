package starb.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import starb.domain.game.Board;
import starb.server.repo.BoardRepository;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(path="board")
public class BoardController {

    private final BoardRepository repo;

    public BoardController(BoardRepository repo) {
        this.repo = repo;
    }

    // Obtain a list of all boards by IDs.
    @GetMapping
    public ArrayList<Integer> getBoards() {

        ArrayList<Integer> ids = new ArrayList<>();

        for (Board board : repo.findAll()) {
            ids.add(board.getID());
        }

        return ids;

    }

    @PostMapping({"id"})
    public Board postBoard(Board board) {
        return repo.save(board);
    }

    // Obtain a specific board
    @RequestMapping("{id}")
    @GetMapping
    public Board getBoard(@PathVariable String id) {

        Optional<Board> board = repo.findById(id);

        if (board.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }

        return board.get();
    }

    @RequestMapping("{id}")
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteBoard(@PathVariable String id) {
        repo.deleteById(id);
    }


}
