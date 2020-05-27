package Server;

import org.dyn4j.geometry.Vector2;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket client;
    private String playerID;
    private GameData gameData;

    public ClientThread(Socket client, GameData gameData) {
        this.client = client;
        this.gameData = gameData;
    }

    @Override
    public void run() {
        boolean running = true;
        try (DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
             DataInputStream dataIn = new DataInputStream(client.getInputStream());
             ObjectOutputStream objectOut = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream objectIn = new ObjectInputStream(client.getInputStream())) {

            dataOut.writeUTF("FuriousFlamingos Server 1.0\n");
            System.out.println("sent: FuriousFlamingos Server 1.0\n");
            dataOut.writeUTF("Player ID?\n");
            System.out.println("Sent: Player ID");
            String playerAnswer = dataIn.readUTF();
            this.playerID = playerAnswer;
            System.out.println("Server got: " + playerAnswer);
            if (gameData.getPlayer1() == null || gameData.getPlayer1().isEmpty()) {
                gameData.setPlayer1(this.playerID);
            } else if (gameData.getPlayer2() == null || gameData.getPlayer2().isEmpty()) {
                gameData.setPlayer2(this.playerID);
            }

            gameData.setNumberOfPlayers(gameData.getNumberOfPlayers() + 1);

            while (gameData.getNumberOfPlayers() < 2) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            dataOut.writeUTF("start");

            while (running && gameData.isRunning()) {
                String command = dataIn.readUTF();
                switch (command) {
                    case "quit":
                        running = false;
                        gameData.stopGame();
                        System.out.println("The game has ended");
                        return;

                    case "getData":
                        System.out.println("Sending gameData");
                        objectOut.writeObject(this.gameData);
                        return;

                    case "endTurn":
                        this.gameData.setPlayer1Turn(!this.gameData.isPlayer1Turn());
                        this.gameData.setHasShot(false);
                        System.out.println("Ended the turn");
                        return;

                    case "shoot":
                        this.gameData.setHasShot(true);
                        try {
                            Object o = objectIn.readObject();
                            if(o instanceof Vector2){
                                this.gameData.setBirdLocation((Vector2) o);
                            }
                            o = objectIn.readObject();
                            if(o instanceof Vector2){
                                this.gameData.setBirdForce((Vector2) o);
                            }
                        }

                        catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }
                        System.out.println("Shot data recieved");


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
