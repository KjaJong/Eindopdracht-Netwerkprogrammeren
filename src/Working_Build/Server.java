package Working_Build;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hugo on 5/18/16.
 */
public class Server {

    private DataInputStream in;
    private DataOutputStream out;
    private byte[] data;
    private Server server;

    public Server() throws IOException {
        ServerSocket server = new ServerSocket(9001);
        while(true){
            Socket socket = server.accept();
            Thread thread = new Thread(new WorkerThread(socket));
            thread.start();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}

class WorkerThread implements Runnable {

    protected Socket socket = null;
    protected DataInputStream in;
    protected DataOutputStream out;
    protected Socket[] sockets = new Socket[5];
    private int socketCounter = 0;
    protected boolean serverOnline = true;

    public WorkerThread(Socket socket) {
        this.socket = socket;
        sockets[socketCounter] = socket;
        socketCounter = sockets.length;

        if(socketCounter >= 5){
            System.out.println("Server is full");
        }
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            /*while(serverOnline){
                //TODO listen for request from the gui
            }*/
            sendImage();

            out.close();
            in.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendImage() {
        //YES, YES TERRY! SEND, THE IMAGE!!!
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/Resources/snaaaaake.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("I'm firin' mah byte stream!");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "PNG", os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bytes = os.toByteArray();

        System.out.println(bytes.length);
        try {
            out.writeInt(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                out.writeByte(bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}