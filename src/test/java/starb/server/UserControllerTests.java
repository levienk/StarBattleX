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

import java.util.List;

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
        User newUser = controller.postUser();

        assertNotNull(newUser);

        assertNotNull(newUser.getId());

        assertEquals("", newUser.getNextPuzzle());

        assertTrue(newUser.getCompleted().isEmpty());
        assertTrue(newUser.getInaccessible().isEmpty());

        assertTrue(repo.findById(newUser.getId()).isPresent());
    }
    @Test
    public void testPutUserSuccess() {
        User updatedUserData = new User();
        updatedUserData.setNextPuzzle("Puzzle2");
        updatedUserData.setCompleted(List.of("Puzzle0", "Puzzle1"));

        ResponseEntity<User> response = controller.putUser(existingUser.getId(), updatedUserData);

        assertEquals(200, response.getStatusCodeValue());
        User updatedUser = response.getBody();
        assertNotNull(updatedUser);
        assertEquals("Puzzle2", updatedUser.getNextPuzzle());
        assertTrue(updatedUser.getCompleted().containsAll(List.of("Puzzle0", "Puzzle1")));
    }
    @Test
    public void testPutUserNotFound() {
        // Prepare updated user data
        User updatedUserData = new User();
        updatedUserData.setNextPuzzle("Puzzle2");
        updatedUserData.setCompleted(List.of("Puzzle0", "Puzzle1"));

        // Attempt to update a non-existing user
        ResponseEntity<User> response = controller.putUser("nonExistingId", updatedUserData);

        // Validate the response
        assertEquals(404, response.getStatusCodeValue());
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
