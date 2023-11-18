package starb.domain.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("users")
public class User {
    @Id
    private String id;
    private List<Integer> completed;
    private int nextPuzzle;
    private List<Integer> inaccessible;

    /**
     * Constructor for building a user from the server's data
     * @param id The user's id
     * @param completed The list of completed puzzles
     * @param nextPuzzle The id of the next puzzle
     * @param inaccessible The list of inaccessible puzzles
     */
    @JsonCreator
    public User(@JsonProperty("id") String id,
                @JsonProperty("completed") List<Integer> completed,
                @JsonProperty("nextPuzzle") int nextPuzzle,
                @JsonProperty("inaccessible") List<Integer> inaccessible
                ) {
        this.id = id;
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
        this.completed = new ArrayList<>();
        this.nextPuzzle = -1; // ID of the first puzzle
        this.inaccessible = new ArrayList<>(); // All other puzzles
    }

    public void updateNextPuzzle(){
        if (this.nextPuzzle >= 0) { // If there is a listed next puzzle
            // Register the previously incomplete next puzzle as complete
            this.completed.add(this.nextPuzzle);
        }
        if (!inaccessible.isEmpty()) { // If there are more puzzles
            this.nextPuzzle = inaccessible.get(0); // set the next puzzle
            inaccessible.remove(0); // And remove it from inaccessible
        }
        else { // If this was the last puzzle
            this.nextPuzzle = -1; // Update the next puzzle String to show that no more files exist
        }
    }

    public String getId() {return this.id;}
    public void setId(String id){this.id = id;}

    public List<Integer> getCompleted(){return completed;}
    public void setCompleted(List<Integer> completed){this.completed = completed;}

    public int getNextPuzzle(){return this.nextPuzzle;}
    public void setNextPuzzle(int nextPuzzle){this.nextPuzzle = nextPuzzle;}
    public List<Integer> getInaccessible() {return inaccessible;}
    public void setInaccessible(List<Integer> ids) {
        this.inaccessible = ids;
    }

    @JsonIgnore
    @Transient
    public String getPlayerRank() {
        String playerRank;
        if (this.completed.isEmpty()) {
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
