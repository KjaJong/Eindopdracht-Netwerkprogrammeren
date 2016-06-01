package Sockets;

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
    int port = 8000;
    DataInputStream in;
    DataOutputStream out;
    ServerSocket server;
    Socket clientSocket;

    /**
     * The constructor for the server setup
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
        try{
            clientSocket = server.accept();
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println(in.readDouble());
            out.writeDouble(100.2);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //<editor-fold desc="Getters and setters">
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

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

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    //</editor-fold>
}
