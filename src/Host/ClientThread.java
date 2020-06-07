package Host;

import Game.Logic.ScoreSystem;
import org.dyn4j.dynamics.Force;
import org.dyn4j.geometry.Vector2;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket client;
    private String playerID;

    public ClientThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        boolean running = true;
        try (DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
             DataInputStream dataIn = new DataInputStream(client.getInputStream());
             ObjectOutputStream objectOut = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream objectIn = new ObjectInputStream(client.getInputStream())) {

            //Making sure communication with the client is ok
            dataOut.writeUTF("FuriousFlamingos Host 1.0\n");
            System.out.println("sent: FuriousFlamingos Host 1.0\n");
            dataOut.writeUTF("Player ID?\n");
            System.out.println("Sent: Player ID");
            String playerAnswer = dataIn.readUTF();
            this.playerID = playerAnswer;
            System.out.println("Host got: " + playerAnswer);
            ScoreSystem.getInstance().setPlayer2(playerID);

            //give the start command
            dataOut.writeUTF("start");

            //Checks for messages while the game is running
            while (running && ScoreSystem.getInstance().isRunning()) {
                String command = dataIn.readUTF();
                System.out.println("Got command: "+command);
                switch (command) {
                    case "quit":
                        running = false;
                        ScoreSystem.getInstance().stopGame();
                        System.out.println("The game has ended");
                        break;

                        //sends the most up to date instance of ScoreSystem
                    case "getData":
                        System.out.println("RedScore: "+ScoreSystem.getInstance().getRedScore());
                    objectOut.writeObject(ScoreSystem.getInstance());
                    objectOut.reset();
                    break;

                    //Receives the shot data of the Client
                    case "shoot":
                        try {
                            Object o = objectIn.readObject();
                            if(o instanceof double[]){
                                double[] birdForce =(double[]) o;
                                ScoreSystem.getInstance().setBirdForce(birdForce[0], birdForce[1]);
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
