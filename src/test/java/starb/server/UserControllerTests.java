package starb.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import starb.domain.json.User;
import starb.server.controller.UserController;
import starb.server.repo.UserRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTests {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private User existingUser;

    @Autowired
    private UserRepository repo;

    @Autowired
    private UserController controller;
    private HttpClient client = HttpClient.newHttpClient();

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

        assertEquals(-1, newUser.getNextPuzzle());

        assertTrue(newUser.getCompleted().isEmpty());
        assertTrue(newUser.getInaccessible().isEmpty());

        assertTrue(repo.findById(newUser.getId()).isPresent());
    }
    @Test
    public void testPutUser() throws Exception {
        User updatedUserData = new User();
        updatedUserData.setNextPuzzle(2);
        updatedUserData.setCompleted(List.of(0, 1));

        User user = controller.putUser(existingUser.getId(), updatedUserData);

        // Test that the HTTP Status Code is 200
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(updatedUserData);
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:" + port + "/user/" + existingUser.getId()))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        assertNotNull(user);
        assertEquals(2, user.getNextPuzzle());
        assertTrue(user.getCompleted().containsAll(List.of(0, 1)));
    }
    @Test
    public void testPutUserNotFound() throws Exception {
        // Prepare updated user data
        User updatedUserData = new User();
        updatedUserData.setNextPuzzle(2);
        updatedUserData.setCompleted(List.of(0, 1));

        // Attempt to update a non-existing user
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(updatedUserData);
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:" + port + "/user/" + "nonExistingId"))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Validate the response
        // Test for if the 404 error is thrown properly
        assertEquals(404, response.statusCode());
    }
    @Test
    public void testGetUser() throws Exception {
        // Perform GET
        User user = controller.getUser(existingUser.getId());
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:" + port + "/user/" + existingUser.getId()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Test status code 200
        assertEquals(200, response.statusCode());

        assertNotNull(user);
        assertEquals(existingUser.getId(), user.getId());
    }
    @Test
    public void testGetUserNotFound() throws Exception {
        // Perform GET
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:" + port + "/user/" + "nonExistingId"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
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
