package Main;

import Sockets.Server;

/**
 * Created by Menno on 1-6-2016.
 */
public class ServerMain {
    private Server server;

    public static void main(String[] args){
        new ServerMain();
    }

    public ServerMain(){
        server = new Server();
    }
}
