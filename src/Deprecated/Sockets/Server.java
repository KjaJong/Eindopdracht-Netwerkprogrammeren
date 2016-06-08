package Deprecated.Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Menno de Jong on 1-6-2016. Sets up a server side.
 */
//TODO: Is this going to make extra threads in case of multiple users?
public class Server{
    int port = 9001;
    DataInputStream in;
    DataOutputStream out;
    ServerSocket server;
    Socket socket;

    /**
     *
     */
    public Server(){
        try{
            server = new ServerSocket(port);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        runServer();
    }

    private void runServer(){
        while(true){
            try{
                socket = server.accept();
            }
            catch(IOException e){
                e.printStackTrace();
            }

            try{
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
