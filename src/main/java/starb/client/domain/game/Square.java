package starb.client.domain.game;

public class Square {
    private String state = "";
    private boolean isStarValid = true;

    protected void setState(String state){
        this.state = state;
    }
}
