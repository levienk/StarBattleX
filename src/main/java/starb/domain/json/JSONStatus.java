package starb.domain.json;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class JSONStatus {
    private List<String> completed;
    private String nextPuzzle;
    private List<String> inaccessible;
    private List<EventListener> eventListeners;

    public JSONStatus() {
        // Each of the Strings are JSON File names
        //this.completed = server's data for completed;
        //this.nextPuzzle = server's data for nextPuzzle;
        //this.inaccessible = server's data for inaccessible;
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

    public List<String> getCompleted(){return completed;}

    public String getNextPuzzle(){return this.nextPuzzle;}

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
        return playerRank;
    }
}
