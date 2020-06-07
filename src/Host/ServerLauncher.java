package Host;

import Game.Logic.ScoreSystem;

import java.util.Scanner;

public class ServerLauncher {
    public static void main(String args[]){
        ScoreSystem scoreSystem = new ScoreSystem(15);
        scoreSystem.setPlayer1("Host");

        Server server = new Server(6969);


        Thread serverThread = new Thread(server);
        serverThread.start();

    }
}
