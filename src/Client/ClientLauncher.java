package Client;

import Server.GameData;

import java.util.Scanner;

public class ClientLauncher {
    public static void main(String args[]){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter Player ID:");
        String playerID = reader.nextLine();
        ClientCommunication client = new ClientCommunication(playerID);
        client.connect("localhost", 6969);

        GameData gameData = client.getGameData();
        client.endTurn();
        while(gameData.isRunning()){
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
