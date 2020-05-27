package Server;

public class ServerLauncher {
    public static void main(String args[]){
        Server server = new Server(6969);

        Thread serverThread = new Thread(server);
        serverThread.start();
        while (true){
            try{
                Thread.sleep(10000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
