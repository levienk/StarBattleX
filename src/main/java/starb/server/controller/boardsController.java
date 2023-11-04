package starb.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="board")
public class boardController {

    @Autowired
    private boardRepository repo;

    // Obtain a list of all boards
    @GetMapping
    public String getBoards() {
        return "...";
    }

    // Obtain a specific board
    @GetMapping(path={id})
    public String getBoard() {
        return "...";
    }



}
