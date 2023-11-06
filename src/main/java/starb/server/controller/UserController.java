package starb.server.controller;

import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import starb.domain.json.User;
import starb.server.repo.UserRepository;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository users;

    @PostMapping
    public User postUser(){
        User doc = new User();
        users.save(doc);
        return null;
    }

    @RequestMapping("{id}")
    @PutMapping
    public User putUser(@PathVariable String id, @RequestBody User user){
        return null;
    }

    @RequestMapping("{id}")
    @GetMapping(produces="application/json")
    public User getUser(@PathVariable String id) {
        return null;
    }

    @RequestMapping("{id}")
    @DeleteMapping
    public void deleteUser(@PathVariable String id){
        //
    }
}
