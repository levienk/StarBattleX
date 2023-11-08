package starb.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import starb.domain.json.User;
import starb.server.controller.UserController;
import starb.server.repo.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTests {
    private User existingUser;

    @Autowired
    private UserRepository repo;

    @Autowired
    private UserController controller;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
        existingUser = new User();
        existingUser = repo.save(existingUser);
    }
    @Test
    public void testPostUser() {

    }
    @Test
    public void testPutUserNotFound() {

    }
    @Test
    public void testGetUser() {
        ResponseEntity<User> response = controller.getUser(existingUser.getId());
        assertEquals(200, response.getStatusCodeValue());
        User foundUser = response.getBody();
        assertNotNull(foundUser);
        assertEquals(existingUser.getId(), foundUser.getId());
    }
    @Test
    public void testGetUserNotFound() {
        ResponseEntity<User> response = controller.getUser("nonExistingId");
        assertEquals(404, response.getStatusCodeValue());
    }
    @Test
    public void testDeleteUser() {
        assertDoesNotThrow(() -> controller.deleteUser(existingUser.getId()));
        assertFalse(repo.existsById(existingUser.getId()));
    }
    @Test
    public void testDeleteUserNotFound() {
        assertDoesNotThrow(() -> controller.deleteUser("nonExistingId"));
        assertEquals(1, repo.count());
    }
}
