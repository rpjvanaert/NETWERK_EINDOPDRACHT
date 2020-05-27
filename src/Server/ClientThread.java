package Server;

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
    try(DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
        DataInputStream dataIn = new DataInputStream(client.getInputStream());
        ObjectOutputStream objectOut = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream objectIn = new ObjectInputStream(client.getInputStream())){

        dataOut.writeUTF("FuriousFlamingos Server 1.0\n");
        System.out.println("sent: FuriousFlamingos Server 1.0\n");
        dataOut.writeUTF("Player ID?\n");
        System.out.println("Sent: Player ID");
        String playerAnswer = dataIn.readUTF();
        this.playerID = playerAnswer;
        System.out.println("Server got: "+playerAnswer);
        if(gameData.getPlayer1() == null || gameData.getPlayer1().isEmpty()){
            gameData.setPlayer1(this.playerID);
        }
        else if(gameData.getPlayer2() == null || gameData.getPlayer2().isEmpty()){
            gameData.setPlayer2(this.playerID);
        }

        gameData.setNumberOfPlayers(gameData.getNumberOfPlayers()+1);

        while (gameData.getNumberOfPlayers() < 2){
            try {
                Thread.sleep(1000);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        dataOut.writeUTF("start");

        client.close();
        System.out.println("Player "+playerID+" disconnected!");
    }
    catch (IOException e){
        System.out.println("Error creating data streams");
        e.printStackTrace();
    }


    }
}
