package Host;

import Game.Logic.ScoreSystem;
import org.dyn4j.dynamics.Force;
import org.dyn4j.geometry.Vector2;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket client;
    private String playerID;
    private ScoreSystem scoreSystem;

    public ClientThread(Socket client, ScoreSystem scoreSystem) {
        this.client = client;
        this.scoreSystem = scoreSystem;
    }

    @Override
    public void run() {
        boolean running = true;
        try (DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
             DataInputStream dataIn = new DataInputStream(client.getInputStream());
             ObjectOutputStream objectOut = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream objectIn = new ObjectInputStream(client.getInputStream())) {

            dataOut.writeUTF("FuriousFlamingos Host 1.0\n");
            System.out.println("sent: FuriousFlamingos Host 1.0\n");
            dataOut.writeUTF("Player ID?\n");
            System.out.println("Sent: Player ID");
            String playerAnswer = dataIn.readUTF();
            this.playerID = playerAnswer;
            System.out.println("Host got: " + playerAnswer);
            scoreSystem.setPlayer2(playerID);


            dataOut.writeUTF("start");

            while (running && scoreSystem.isRunning()) {
                String command = dataIn.readUTF();
                System.out.println("Got command: "+command);
                switch (command) {
                    case "quit":
                        running = false;
                        scoreSystem.stopGame();
                        System.out.println("The game has ended");
                        break;

                    case "getData":
                        this.scoreSystem = ScoreSystem.getInstance();
                        System.out.println("Red score: "+this.scoreSystem.getRedScore());
                        System.out.println("Sending gameData");
                        objectOut.writeObject(this.scoreSystem);
                        break;

                    case "endTurn":
                        this.scoreSystem.turn();
                        System.out.println("Ended the turn");
                        break;

                    case "shoot":
                        try {
                            Object o = objectIn.readObject();
                            if(o instanceof double[]){
                                double[] birdForce =(double[]) o;
                                Force force = new Force(birdForce[0],birdForce[1]);
                                this.scoreSystem.setBirdForce(force);
                            }
                        }

                        catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }
                        System.out.println("Shot data recieved");
                        break;
                }
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

            }

            client.close();
            System.out.println("Player " + playerID + " disconnected!");
        } catch (IOException e) {
            System.out.println("Error creating data streams");
            e.printStackTrace();
        }


    }
}
