package starb.client.domain.game;

public class Square {
    private String state;
    private boolean isStarValid;

    public Square(){
        this.state = "";
        this.isStarValid = true;
    }

    protected void setState(String state){
        this.state = state;
    }
    public String getState() { return state;}
}
