package Working_Build;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

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
            objOut = new ObjectOutputStream(socket.getOutputStream());
            objOut.flush();

            objOut.writeObject(new Meme(ImageIO.read(new File("src/Resources/Rickrolls.jpg")), Categories.TROLL, 0));


            objIn = new ObjectInputStream(socket.getInputStream());

            Commands com = null;

            while(serverOnline){
                try{
                    com = (Commands) objIn.readObject();
                }
                catch(java.net.SocketException e){
                    Thread.currentThread().interrupt();
                    return;
                } catch(ClassNotFoundException f){
                    f.printStackTrace();
                }

                switch(com) {

                    case ACCESS: //Access the database by returning the what kind of image question
                        System.out.println("ACCESS");
                        objOut.writeObject(getBrowser());
                        String path = null;
                        while (path != "stop") {
                            path = null;
                            try {
                            path = (String) objIn.readObject();
                                if (path.equals("stop")){
                                    System.out.println("Caught STOP");
                                    break;
                                } else{
                                    System.out.println(path + " is not the same as: stop");
                                }

                            } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            }
                            System.out.println("Attempting to send: " + path);
                            objOut.writeObject(new Meme(ImageIO.read(new File(path)), Categories.TROLL, 0));
                        }
                        break;
                    case MODIFY: //Access the database modifier
                        System.out.println("MODIFY");
                        try{
                            ArrayList<String> paths = searchMeme(objIn.readUTF());
                            objOut.writeObject(createMeme(paths));
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                        break;
                    case EXIT:
                        System.out.println("EXIT");
                        break;
                    case RICKROLL:
                        try{
                            objOut.writeObject(new Meme(ImageIO.read(new File("src/Resources/Rickrolls.jpg")), Categories.TROLL, 0));
                            System.out.println("Sending rickroll.");
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                        System.out.println("Never gonna give you up, never gonna let you down, never gonna run around and hurt you.");
                        break;

                }
            }
            //socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Thumbnail> getBrowser() throws IOException {
        ArrayList<String> files = searchMeme("src/Resources/");
        Collections.sort(files); //Sorts the meme on ratings
        Collections.reverse(files);//Sets the highest rating first
        ArrayList<Thumbnail> thumbnails = new ArrayList<Thumbnail>();
        for(int i = 0; i < files.size(); i++){
            thumbnails.add(new Thumbnail(new ImageIcon(Scalr.resize(ImageIO.read(new File(files.get(i))), 150)), files.get(i)));
        }
        return thumbnails;
    }

    private ArrayList<String> searchMeme(String path) throws IOException{
        File root = new File( path );
        File[] list = root.listFiles();
        ArrayList<String> files = new ArrayList<>();

        if (list == null){
            return null;
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
        return files;
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

    private ArrayList<Meme> createMeme(ArrayList<String> paths) throws IOException {
        ArrayList<Meme> memes = new ArrayList<>();

        for(String path:paths){
            Meme meme = new Meme(ImageIO.read(new File(path)), Categories.randomCategorie(), (int)(Math.random()*1000));
            memes.add(meme);
        }

        return memes;
    }
}