package Game.Logic;

import org.dyn4j.dynamics.Force;
import org.dyn4j.geometry.Vector2;

import java.io.Serializable;

public class ScoreSystem implements Serializable {
    private int maxScore;
    private int redScore;
    private int blueScore;
    private boolean redTurn;
    private boolean isRunning;
    private String player1;
    private String player2;
    private int numberOfPlayers;
    private double birdForceX;
    private double birdForceY;

    private static ScoreSystem instance = null;

    /**
     * ScoreSystem (Constructor)
     * Creates ScoreSystem Object
     * Contains MaxScore which can be set and every other needed information about game.
     * @param maxScore
     */
    public ScoreSystem(int maxScore) {
        this.maxScore = maxScore;
        this.redScore = 0;
        this.blueScore = 0;
        this.redTurn = true;
        this.isRunning = false;
        this.player1 = null;
        this.player2 = null;
        this.numberOfPlayers = 0;
        this.birdForceX = 0;
        this.birdForceY = 0;

    }

    /**
     * getInstance
     * Singleton pattern. returns ScoreSystem of 15.
     * @return ScoreSystem
     */
    public static ScoreSystem getInstance(){
        if (instance == null){
            instance = new ScoreSystem(15);
        }
        return instance;
    }

    /**
     * setInstance
     * Sets Singleton instance to given ScoreSystem.
     * @param newScoreSystem
     */
    public static void setInstance(ScoreSystem newScoreSystem){
        if(newScoreSystem != null){
            instance = newScoreSystem;
        }
    }

    /**
     * reset
     * Creates a new instance of ScoreSystem with a maxScore of 15
     */
    public void reset(){
        instance = new ScoreSystem(15);
    }

    /**
     * addRed
     * Adds one score to red if it's red's turn.
     */
    public void addRed() {
        if (this.redTurn){
            this.redScore++;
        }
    }

    /**
     * addBlue
     * Adds one score to blue if it's blue's turn.
     */
    public void addBlue() {
        if (!this.redTurn){
            this.blueScore++;
        }
    }

    /**
     * isOver
     * Returns true if maxScore is achieved
     * @return boolean
     */
    public boolean isOver(){
        if (redScore + blueScore >= maxScore){
            return true;
        }
        return false;
    }

    /**
     * start
     * Sets running to true for the game.
     */
    public void start(){
        this.isRunning = true;
    }

    /**
     * isRedTurn
     * Returns true if it's red's turn.
     * @return boolean
     */
    public boolean isRedTurn(){ return this.redTurn; }

    /**
     * turn
     * Sets turn to red from blue or from blue to red.
     */
    public void turn(){ this.redTurn = !this.redTurn; }

    /**
     * toString
     * Returns String of the score.
     * @return String
     */
    @Override
    public String toString() {
        return "Red: " + this.redScore + " - Blue: " + this.blueScore;
    }

    /**
     * stopGame
     * Sets running to false for the game.
     */
    public void stopGame(){
        this.isRunning = false;
    }

    /**
     * isRunning
     * Returns true if game is running.
     * @return boolean
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * getPlayer1
     * Returns player1's name.
     * @return String
     */
    public String getPlayer1() {
        return player1;
    }

    /**
     * setPlayer1
     * Sets player1's name.
     * @param player1
     */
    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    /**
     * getPlayer2
     * Returns player1's name.
     * @return String
     */
    public String getPlayer2() {
        return player2;
    }

    /**
     * setPlayer2
     * Sets player2's name.
     * @param player2
     */
    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    /**
     * getRedScore
     * Returns red's score.
     * @return int
     */
    public int getRedScore() {
        return redScore;
    }

    /**
     * getBlueScore
     * Return blue's score.
     * @return int
     */
    public int getBlueScore() {
        return blueScore;
    }

    /**
     * setBirdForce
     * Sets birdforce x and y component.
     * @param x double
     * @param y double
     */
    public void setBirdForce(double x, double y){
        this.birdForceY = y;
        this.birdForceX = x;
    }

    /**
     * getBirdForce
     * Returns birdforce as Force Object.
     * @return Force
     */
    public Force getBirdForce(){
        return new Force(birdForceX, birdForceY);
    }
}
