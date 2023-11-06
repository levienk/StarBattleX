package starb.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import starb.server.repo.UserRepository;

@RestController
@RequestMapping(path="example")
public class ExampleController {

    @GetMapping
    public String exampleGet() {
        return "Hello from your SpringBoot server!";
    }

}
