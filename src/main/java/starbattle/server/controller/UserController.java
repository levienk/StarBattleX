package starbattle.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import starbattle.domain.game.Board;
import starbattle.domain.user.User;
import starbattle.server.repo.BoardRepository;
import starbattle.server.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserRepository users;
    private final BoardRepository boards;

    public UserController(UserRepository users, BoardRepository boards) {
        this.users = users;
        this.boards = boards;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(){
        System.out.println("hello");
        User doc = new User();
        // Grab the board ids
        List<Integer> boardIds = new ArrayList<>();
        for (Board element:boards.findAll()) {
            boardIds.add(element.getID());
        }
        doc.setInaccessible(boardIds); // Populate the inaccessible list in ascending order
        doc.updateNextPuzzle();
        return users.save(doc);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User putUser(@PathVariable String id, @RequestBody User userInput){
        // The user identified by the id
        User updatedUser;
        if (users.existsById(id)){
            userInput.setId(id);
            // Perform the update on a field in updatedUser
            updatedUser = users.save(userInput);
            // Return the updated User with a 200 OK response
            return updatedUser;
        }
        // Return a 404 Not Found response if the user with the specified ID was not found
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User with matching ID");
    }

    @GetMapping(value = "{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable String id) {
        Optional<User> target = users.findById(id);
        if (target.isEmpty()){
            // Return 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User with matching ID");
        }
        // Return 200 OK with the User object
        return target.get();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id){
        // Check if the id is in use
        if (users.existsById(id)) {
            // No returns needed for deletes
            users.deleteById(id);
        }
    }
}
