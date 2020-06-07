package Game.Logic;

public class ScoreSystem {
    private int maxScore;
    private int redScore;
    private int blueScore;
    private boolean redTurn;

    private static ScoreSystem instance = null;

    public ScoreSystem(int maxScore) {
        this.maxScore = maxScore;
        this.redScore = 0;
        this.blueScore = 0;
        this.redTurn = true;
    }

    public static ScoreSystem getInstance(){
        if (instance == null){
            instance = new ScoreSystem(8);
        }
        return instance;
    }

    public void reset(){
        instance = new ScoreSystem(8);
    }

    public void addRed() {
        if (this.redTurn){
            this.redScore++;
        }
    }

    public void addBlue() {
        if (!this.redTurn){
            this.blueScore++;
        }
    }

    public boolean isOver(){
        if (redScore + blueScore >= maxScore){
            return true;
        }
        return false;
    }

    public boolean isRedTurn(){ return this.redTurn; }

    public void turn(){ this.redTurn = !this.redTurn; }

    @Override
    public String toString() {
        return "Red: " + this.redScore + " - Blue: " + this.blueScore;
    }
}
