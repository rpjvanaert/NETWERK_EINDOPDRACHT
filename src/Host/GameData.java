package Host;

import org.dyn4j.geometry.Vector2;

import java.io.Serializable;

public class GameData implements Serializable {
   private boolean hasStarted;
   private boolean isRunning;
   private boolean hasShot;
   private int firstPlayerBoxes;
   private int secondPlayerBoxes;
   private boolean isPlayer1Turn;
   private Vector2 birdLocation;
   private Vector2 birdForce;
   private String player1;
   private String player2;
   private int numberOfPlayers;


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public GameData(int boxesPerPlayer){
        this.isRunning = true;
        this.hasShot = false;
        this.firstPlayerBoxes = boxesPerPlayer;
        this.secondPlayerBoxes = boxesPerPlayer;
        this.isPlayer1Turn = true;
        this.hasStarted = false;
        this.birdLocation = null;
        this.birdForce = null;
        this.player1 = null;
        this.player2 = null;
        this.numberOfPlayers = 0;

    }

    public void start(){
        this.hasStarted = true;
    }

    public void stopGame(){
        this.isRunning = false;
    }

    public boolean isHasStarted() {
        return hasStarted;
    }


    public boolean isRunning() {
        return isRunning;
    }

    public boolean isHasShot() {
        return hasShot;
    }

    public void setHasShot(boolean hasShot) {
        this.hasShot = hasShot;
    }

    public int getFirstPlayerBoxes() {
        return firstPlayerBoxes;
    }

    public void setFirstPlayerBoxes(int firstPlayerBoxes) {
        this.firstPlayerBoxes = firstPlayerBoxes;
    }

    public int getSecondPlayerBoxes() {
        return secondPlayerBoxes;
    }

    public void setSecondPlayerBoxes(int secondPlayerBoxes) {
        this.secondPlayerBoxes = secondPlayerBoxes;
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

    public Vector2 getBirdLocation() {
        return birdLocation;
    }

    public void setBirdLocation(Vector2 birdLocation) {
        this.birdLocation = birdLocation;
    }

    public Vector2 getBirdForce() {
        return birdForce;
    }

    public void setBirdForce(Vector2 birdForce) {
        this.birdForce = birdForce;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        isPlayer1Turn = player1Turn;
    }
}
