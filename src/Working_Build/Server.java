package Working_Build;

import javax.imageio.ImageIO;
import java.awt.im.InputContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
            /**
            Commands com = null;

            while(serverOnline){
                try{
                    com = (Commands)objIn.readObject();
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                switch(com){
                    case ACCESS:
                        searchMeme("/home/hugo/Downloads/DankMemes");
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

            }
            **/
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void searchMeme(String path) throws IOException{
        File root = new File( path );
        File[] list = root.listFiles();
        files = new ArrayList();

        if (list == null){
            return;
        }

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                searchMeme( f.getAbsolutePath() );
                System.out.println( "Dir:" + f.getAbsoluteFile() );
                files.add( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                System.out.println( "" + f.getAbsoluteFile() );
                files.add( "" + f.getAbsoluteFile() );
            }
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