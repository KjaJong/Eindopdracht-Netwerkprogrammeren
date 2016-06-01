package Sockets;

/**
 * Created by Menno on 1-6-2016.
 */
public class ClientTask implements Runnable{
    private Client client;

    public ClientTask(){

    }

    public void run(){
        client = new Client(8000, "LocalHost");
    }
}
