package Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Menno on 1-6-2016.
 */
public class Client {
    int port;
    String host;
    DataInputStream in;
    DataOutputStream out;
    Socket socket;

    public Client(int port, String host){
        this.port = port;
        this.host = host;

        try{
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
