package Client;

import Host.GameData;

import java.util.Scanner;

public class ClientLauncher {
    public static void main(String args[]){
        Scanner reader = new Scanner(System.in);
        boolean isFetching = false;
        System.out.println("Enter Player ID:");
        String playerID = reader.nextLine();
        ClientCommunication client = new ClientCommunication(playerID);
        client.connect("localhost", 6969);

        GameData gameData = client.getGameData();
        System.out.println("Host name: " +gameData.getPlayer1());
        client.endTurn();
        client.quit();
        System.out.println("Quitting the game");
        while(gameData.isRunning()){

            if(!isFetching) {
                isFetching = true;
                gameData = client.getGameData();
                isFetching = false;
            }
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }


        }
        System.out.println("The game has ended");
//        while(gameData.isRunning()){
//            try {
//                gameData = client.getGameData();
//                Thread.sleep(5000);
//            }
//            catch (InterruptedException e){
//                e.printStackTrace();
//            }
//
//            System.out.println("Player 1 turn:" + gameData.isPlayer1Turn());
//
//        }
    }
}
