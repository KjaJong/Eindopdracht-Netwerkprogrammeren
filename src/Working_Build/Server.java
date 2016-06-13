package Working_Build;

import javax.imageio.ImageIO;
import java.awt.im.InputContext;
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
            InputStream commandInput = in;
            ObjectInputStream command = new ObjectInputStream(commandInput);
            Commands com = null;

            while(serverOnline){
                try{
                    com = (Commands)command.readObject();
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                switch(com){
                    case ACCESS:
                        sendImage();//TODO let the user search a meme
                        break;
                    case MODIFY:
                        //TODO let the user modify a meme
                        break;
                    case EXIT:
                        //TODO shutdown client who called exit
                        break;
                    case RICKROLL:
                        //Send rickrolls picture.
                        break;
                    default:
                        System.out.println("You really shouldn't be here. Probably shot a null value or something.");
                        break;
                }
            }
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
            image = ImageIO.read(new File("src/Resources/Miku.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("I'm firin' mah byte stream!");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "JPG", os);
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

    private void compareNatural(Meme meme1, Meme meme2){
        int result = meme1.compareTo(meme2);

        if(result > 0){
            System.out.println("The first meme comes for the second meme in natural order.");
        }
        else if(result == 0){
            System.out.println("The first meme is in the same place as the second meme in natural order.");
        }
        else{
            System.out.println("The first meme comes after the second meme in natural order.");
        }
    }
}