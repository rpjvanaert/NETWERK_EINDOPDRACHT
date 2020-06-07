package Host;

import Game.Logic.ScoreSystem;

import java.util.Scanner;

public class ServerLauncher {
    public static void main(String args[]){
        ScoreSystem scoreSystem = new ScoreSystem(15);
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter Player ID:");
        String playerID = reader.nextLine();
        scoreSystem.setPlayer1(playerID);

        Server server = new Server(6969, scoreSystem);

        //launch game

        Thread serverThread = new Thread(server);
        serverThread.start();

    }
}
