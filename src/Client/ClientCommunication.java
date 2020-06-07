package Client;

import Game.GUI;
import Game.Logic.ScoreSystem;
import org.dyn4j.dynamics.Force;
import org.dyn4j.geometry.Vector2;

import java.io.*;
import java.net.Socket;

import static javafx.application.Application.launch;

public class ClientCommunication {
    private String playerID;
    private boolean isConnected;
    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    public ClientCommunication(String playerID) {
        this.playerID = playerID;
        this.isConnected = false;
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
            System.out.println("Host: "+ server);

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

    public void fetchGameData(){
        try {
            dataOut.writeUTF("getData");
            Object o = objectIn.readObject();
            if(o instanceof  ScoreSystem){
                System.out.println("Red score: "+((ScoreSystem) o).getRedScore());
                ScoreSystem.setInstance((ScoreSystem) o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    public void shoot(double birdForceX, double birdForceY){
        try {
            dataOut.writeUTF("shoot");
            double[] birdForce = new double[2];
            birdForce[0] = birdForceX;
            birdForce[1] = birdForceY;
            objectOut.writeObject(birdForce);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
