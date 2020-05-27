package Client;

import Server.GameData;
import sun.java2d.loops.GraphicsPrimitive;

import java.util.Scanner;

public class ClientLauncher2 {
    GameData gameData;
    public static void main(String args[]){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter Player ID:");
        String playerID = reader.nextLine();
        ClientCommunication client = new ClientCommunication(playerID);
        client.connect("localhost", 6969);

        GameData gameData = client.getGameData();


        for(int i = 0; i < 10; ){
            try {
                gameData = client.getGameData();
                Thread.sleep(5000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("Player 1 turn:" + gameData.isPlayer1Turn());

        }
    }
}
