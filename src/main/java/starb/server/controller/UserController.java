package starb.server.controller;

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
            if (users.existsById(id)){
                userInput.setId(id);
                // Perform the update on a field in updatedUser
                updatedUser = users.save(userInput);
                // Return the updated User with a 200 OK response
                return ResponseEntity.ok(updatedUser);
            }
            // Return a 404 Not Found response if the user with the specified ID was not found
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Handle exceptions or validation errors
            return ResponseEntity.badRequest().build(); // Return a 400 Bad Request response for errors
        }
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        Optional<User> target = users.findById(id);

        // Return 200 OK with the User object
        // Return 404 Not Found
        return target.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
