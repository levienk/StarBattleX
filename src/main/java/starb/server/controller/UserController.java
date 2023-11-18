package starb.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import starb.domain.game.Board;
import starb.domain.json.User;
import starb.server.repo.BoardRepository;
import starb.server.repo.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        User doc = new User();
        // Grab the board ids
        List<Integer> boardIds = boards.findAll()
                .stream().map(Board::getID)
                .toList();
        doc.setInaccessible(boardIds); // Populate the inaccessible list
        System.out.println(doc.getInaccessible());
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
