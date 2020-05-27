package Client;

import Server.GameData;
import org.dyn4j.geometry.Vector2;

import java.io.*;
import java.net.Socket;
import java.util.logging.SocketHandler;

public class ClientCommunication {
    private String playerID;
    private boolean isConnected;
    private GameData gameData;
    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    public ClientCommunication(String playerID) {
        this.playerID = playerID;
        this.isConnected = false;
        this.gameData = null;
        this.socket = null;
        this.dataIn = null;
        this.dataOut = null;
        this.objectIn = null;
        this.objectOut = null;
    }

    public boolean connect(String host, int port){
        if(this.isConnected){
            System.out.println("You are already connected!");
            return true;
        }
        try {
            this.socket = new Socket(host, port);
            this.dataIn = new DataInputStream(this.socket.getInputStream());
            this.dataOut = new DataOutputStream(this.socket.getOutputStream());
            this.objectOut = new ObjectOutputStream(this.socket.getOutputStream());
            this.objectIn = new ObjectInputStream(this.socket.getInputStream());

            String server = this.dataIn.readUTF();
            System.out.println("Server: "+ server);

            String readyCheck = this.dataIn.readUTF();
            if(readyCheck.equals("Player ID?\n")){
                this.dataOut.writeUTF(playerID);
                System.out.println("Player " + playerID + " sent: " +playerID);
            }else {
                this.dataOut.writeUTF("Error");
            }

            start();

        }
        catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    public void start(){
        try {
            String command = dataIn.readUTF();
            System.out.println(this.playerID+": start!");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void quit(){
        try {
            dataOut.writeUTF("quit");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void fetchGameData(){
        try{
            System.out.println("Fetching data...");
            dataOut.writeUTF("getData");
            Object o = objectIn.readObject();
            if(o instanceof GameData){
                this.gameData = (GameData) o;
            }
            System.out.println("Got gameData!");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void endTurn(){
        try{
            dataOut.writeUTF("endTurn");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("End turn");
    }

    public void shoot(Vector2 birdLocation, Vector2 birdForce){
        try {
            dataOut.writeUTF("shoot");
            objectOut.writeObject(birdLocation);
            objectOut.writeObject(birdForce);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public GameData getGameData(){
        fetchGameData();
        return this.gameData;
    }

}
