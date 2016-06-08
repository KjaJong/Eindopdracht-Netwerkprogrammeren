package Test2;

import sun.misc.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by hugo on 5/18/16.
 */
public class Server {

    private static DataInputStream in;
    private static DataOutputStream out;
    private static byte[] data;
    private Server server;

    public Server() {

    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9001);
        while(true){
            Socket socket = server.accept();
            new Thread(new WorkerThread(socket)).start();
        }
    }

}

class WorkerThread implements Runnable {

    protected Socket socket = null;
    protected DataInputStream in;
    protected DataOutputStream out;
    private PrintWriter pw;
    private ArrayList<String> files;

    public WorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            files = new ArrayList<String>();
            walk("/home/hugo/Downloads/DankMemes");
            out.writeInt(files.size());
            pw = new PrintWriter(out, true);
            for(int i = 0; i < files.size(); i++){
                pw.println(files.get(i));
            }
            //pw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String file = br.readLine();
            //br = null;
            System.out.println("Sending: " + file);
            sendImage(file);

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendImage(String file) {
        //YES, YES TERRY! SEND, THE IMAGE!!!
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(file));

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

    public void walk( String path ) throws IOException {
        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null){
            return;
        }

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                System.out.println( "Dir:" + f.getAbsoluteFile() );
                files.add( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                System.out.println( "" + f.getAbsoluteFile() );
                files.add( "" + f.getAbsoluteFile() );
            }
        }
    }
}