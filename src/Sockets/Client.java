package Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Menno on 1-6-2016.
 */
public class Client {
    DataInputStream in;
    DataOutputStream out;
    Socket socket;

    public Client(int port, String host){

        try{
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeDouble(100.1);
            System.out.println(in.readDouble());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //<editor-fold desc="Getters and setters">
    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    //</editor-fold>
}
