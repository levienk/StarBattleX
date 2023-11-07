package starb.server.controller;

import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import starb.domain.json.User;
import starb.server.repo.UserRepository;

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
    public User putUser(@PathVariable String id, @RequestBody User user){
        return null;
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public User getUser(@PathVariable String id) {
        return null;
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id){
        //
    }
}
