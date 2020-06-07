package Game.Logic;

import org.dyn4j.geometry.Vector2;

public class ScoreSystem {
    private int maxScore;
    private int redScore;
    private int blueScore;
    private boolean redTurn;
    private boolean isRunning;
    private Vector2 birdForce;
    private String player1;
    private String player2;
    private int numberOfPlayers;

    private static ScoreSystem instance = null;

    public ScoreSystem(int maxScore) {
        this.maxScore = maxScore;
        this.redScore = 0;
        this.blueScore = 0;
        this.redTurn = true;
        this.isRunning = false;
        this.birdForce = null;
        this.player1 = null;
        this.player2 = null;
        this.numberOfPlayers = 0;
    }

    public static ScoreSystem getInstance(){
        if (instance == null){
            instance = new ScoreSystem(15);
        }
        return instance;
    }

    public void reset(){
        instance = new ScoreSystem(15);
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

    public void start(){
        this.isRunning = true;
    }

    public boolean isRedTurn(){ return this.redTurn; }

    public void turn(){ this.redTurn = !this.redTurn; }

    @Override
    public String toString() {
        return "Red: " + this.redScore + " - Blue: " + this.blueScore;
    }

    public void stopGame(){
        this.isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public Vector2 getBirdForce() {
        return birdForce;
    }

    public void setBirdForce(Vector2 birdForce) {
        this.birdForce = birdForce;
    }
}
