package starb.domain.game;

public class Square {
    private String state;
    private boolean isStarValid;

    public Square(){
        this.state = "";
        this.isStarValid = false;
    }

    protected void setState(String state){
        this.state = state;
    }
    public String getState() { return state;}
    public void setStarValidity(boolean validity) {
        this.isStarValid = validity;
    }

    public boolean getStarValidity() {
        return isStarValid;
    };
}
