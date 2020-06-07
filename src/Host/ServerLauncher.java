package Host;

import java.util.Scanner;

public class ServerLauncher {
    public static void main(String args[]){
        GameData gameData = new GameData(10);
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter Player ID:");
        String playerID = reader.nextLine();
        gameData.setPlayer1(playerID);

        Server server = new Server(6969, gameData);

        //launch game

        Thread serverThread = new Thread(server);
        serverThread.start();

    }
}
