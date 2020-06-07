package Host;

import Game.GUI;
import Game.Logic.ScoreSystem;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class Server implements Runnable{

    private boolean stop;
    private int port;
    private List<Thread> clientThreads;
    private List<Socket> clientSockets;

    public Server(int port) {
        this.port = port;
        this.stop = true;
        this.clientThreads = new ArrayList<>();
        this.clientSockets = new ArrayList<>();
    }

    @Override
    public void run() {
        this.stop = false;

        try {
            ServerSocket socket = new ServerSocket(this.port);
            System.out.println("Host is using port: " + this.port);

            System.out.println("Waiting for clients to connect");
            while (clientThreads.size() != 1) {
                Socket client = socket.accept();
                System.out.println("Client added");
                clientSockets.add(client);
                Thread t =new Thread(new ClientThread(client));
                clientThreads.add(t);
                t.start();
            }
            ScoreSystem.getInstance().start();
            }
        catch(IOException e){
            e.printStackTrace();
        }
        while (true){

        }
    }
}
