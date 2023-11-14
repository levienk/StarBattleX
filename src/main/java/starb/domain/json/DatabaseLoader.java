package starb.domain.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class DatabaseLoader {
    private static final String URL ="http://localhost:8888/";
    private static final String USER_ID_PATH = "Assets/User/userID.txt";
    private static ObjectMapper mapper = new ObjectMapper();
    private static HttpClient client = HttpClient.newHttpClient();

    public static User getUser() throws Exception {
        File userFile = new File(USER_ID_PATH);
        // Add a new User to the database if one doesn't exist for this client
        if (!userFile.exists()) {
            User user = createUser();
            PrintWriter printWriter = new PrintWriter(userFile);
            printWriter.print(user.getId());
            return user;
        } else {
            // Get the User based on userID.txt
            Scanner scanner = new Scanner(userFile);
            String userID = scanner.next();

            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .uri(URI.create(URL + "users/" + userID))
                    .GET()
                    .build();

            HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(resp.body(), User.class);  // Convert from JSON to object
        }
    }

    private static User createUser() throws Exception {
        User user = new User();
        String json = mapper.writeValueAsString(user);
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(URL + "users"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        if( resp.statusCode() == 201 ) {
            return mapper.readValue(resp.body(), User.class);  // Convert from JSON to object
        }
        return null;
    }

    public static void updateUser(User user) throws Exception {
        String json = mapper.writeValueAsString(user);
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(URL + "users/" + user.getId()))
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        if( resp.statusCode() != 200 ) {
            throw new RuntimeException("update user failed with response code: " + resp.statusCode());
        }
    }

}
