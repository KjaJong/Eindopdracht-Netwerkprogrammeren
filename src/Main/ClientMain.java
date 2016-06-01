package Main;

import Sockets.Client;
import Sockets.ClientTask;

/**
 * Created by Menno on 1-6-2016.
 */
public class ClientMain {
    private Client client;

    public static void main(String[] args){
        new ClientMain();
    }

    public ClientMain(){
        ClientTask task = new ClientTask();
        Thread thread = new Thread(task);
        thread.start();
    }
}
