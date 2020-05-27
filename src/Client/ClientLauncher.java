package Client;

import java.util.Scanner;

public class ClientLauncher {
    public static void main(String args[]){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter Player ID:");
        String playerID = reader.nextLine();
        ClientCommunication client = new ClientCommunication(playerID);
        client.connect("localhost", 6969);
    }
}
