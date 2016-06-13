package Working_Build;

import javax.imageio.ImageIO;
import java.awt.im.InputContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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

    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

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
            //in = new DataInputStream(socket.getInputStream());
            //out = new DataOutputStream(socket.getOutputStream());

            objOut = new ObjectOutputStream(socket.getOutputStream());
            objOut.flush();

            objOut.writeObject(new Meme(ImageIO.read(new File("/home/hugo/Downloads/2a50a6de84db99fb075cacf498ea576791fc3bf5547528dbd444ea3dfe48abc7.jpg"))));

            objIn = new ObjectInputStream(socket.getInputStream());
            /**
            try {
                SimpleObject simpleObject = (SimpleObject) objIn.readObject();
                System.out.println("The meaning of life is: " + simpleObject.getTheMeaningOfLife());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            **/

            Commands com = null;

            while(serverOnline){
                try{
                    com = (Commands)objIn.readObject();
                }
                catch(java.net.SocketException e){
                    Thread.currentThread().interrupt();
                    return;
                } catch(ClassNotFoundException f){
                    f.printStackTrace();
                }

                switch(com){
                    case ACCESS: //Access the database by returning the what kind of image question
                        System.out.println("ACCESS");
                        break;
                    case MODIFY: //Access the database modifier
                        System.out.println("MODIFY");
                        break;
                    case EXIT: //TODO shutdown client who called exit
                        System.out.println("EXIT");
                        break;
                    case RICKROLL: //Send rickrolls picture.
                        System.out.println("Never gonna give you up, never gonna let you down, never gonna run around and hurt you.");
                        break;
                    default: System.out.println("You really shouldn't be here. Probably shot a null value or something.");
                        break;

                }

            }

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
            image = ImageIO.read(new File("/home/hugo/Downloads/2a50a6de84db99fb075cacf498ea576791fc3bf5547528dbd444ea3dfe48abc7.jpg"));

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