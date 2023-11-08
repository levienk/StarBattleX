package starb.server.controller;

import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import starb.domain.json.User;
import starb.server.repo.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository users;

    public UserController(UserRepository users) {
        this.users = users;
    }

    @PostMapping
    public User postUser(){
        User doc = new User();
        return users.save(doc);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> putUser(@PathVariable String id, @RequestBody User userInput){
        try {
            // The user identified by the id
            User updatedUser;
            // Perform the update on a field in updatedUser
            updatedUser = users.updateField(id, userInput);
            if (updatedUser != null) {
                // Return the updated User with a 200 OK response
                return ResponseEntity.ok(updatedUser);
            } else {
                // Return a 404 Not Found response if the user with the specified ID was not found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle exceptions or validation errors
            return ResponseEntity.badRequest().build(); // Return a 400 Bad Request response for errors
        }
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        Optional<User> target = users.findById(id);

        if (target.isPresent()) {
            // Return 200 OK with the User object
            return ResponseEntity.ok(target.get());
        }
        else {
            // Return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id){
        // Check if the id is in use
        if (users.existsById(id)) {
            // No returns needed for deletes
            users.deleteById(id);
        }
    }
}
