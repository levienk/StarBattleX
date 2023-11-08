package starb.domain.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

@Document("users")
public class User {
    @Id
    private String id;
    private List<String> completed;
    private String nextPuzzle;
    private List<String> inaccessible;

    /**
     * Constructor for building a user from the server's data
     * @param id
     * @param completed
     * @param nextPuzzle
     * @param inaccessible
     */
    @JsonCreator
    public User(@JsonProperty("id") String id,
                @JsonProperty("completed") List<String> completed,
                @JsonProperty("nextPuzzle") String nextPuzzle,
                @JsonProperty("inaccessible") List<String> inaccessible
                ) {
        this.id = null;
        this.completed = completed;
        this.nextPuzzle = nextPuzzle;
        this.inaccessible = inaccessible;
    }

    /**
     * Constructor for a new User being added to the database
     */
    @JsonCreator
    public User(){
        this.id = null;
        this.completed = new ArrayList<String>();
        this.nextPuzzle = ""; // Id of the first puzzle
        this.inaccessible = new ArrayList<String>(); // All other puzzles
    }

    public void updateNextPuzzle(){
        // Register the previously incomplete next puzzle as complete
        this.completed.add(this.nextPuzzle);

        if (inaccessible.size() > 0) { // If there are more puzzles
            this.nextPuzzle = inaccessible.get(0); // set the next puzzle
            inaccessible.remove(0); // And remove it from inaccessible
        }
        else { // If this was the last puzzle
            this.nextPuzzle = ""; // Update the next puzzle String to show that no more files exist
        }
    }

    public String getId() {return this.id;}

    public List<String> getCompleted(){return completed;}

    public String getNextPuzzle(){return this.nextPuzzle;}
    public List<String> getInaccessible() {return inaccessible;}

    public String getPlayerRank() {
        String playerRank = "beginner";
        if (this.completed.size() == 0) {
            playerRank = "beginner";
        }
        else if (completed.size() < 5) {
            playerRank = "novice";
        }
        else if (completed.size() < 10) {
            playerRank = "intermediate";
        }
        else if (completed.size() < 15) {
            playerRank = "expert";
        }
        else if (completed.size() < 20) {
            playerRank = "master";
        }
        else {
            playerRank = "professional";
        }
        return playerRank;
    }
}
