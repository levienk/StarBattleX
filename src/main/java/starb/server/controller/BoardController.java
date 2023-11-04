package starb.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import starb.server.repo.BoardRepository;

@RestController
@RequestMapping(path="board")
public class BoardController {

    @Autowired
    private BoardRepository repo;

    // Obtain a list of all boards
    @GetMapping
    public String getBoards() {
        return "...";
    }

    // Obtain a specific board
    @RequestMapping("{id}")
    @GetMapping
    public String getBoard() {
        return "...";
    }



}
