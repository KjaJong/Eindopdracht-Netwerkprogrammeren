package Working_Build;

import java.io.*;
import java.net.Socket;

/**
 * Created by hugo on 6/13/16.
 */
public class SimpleObjectClient {

    public static void main (String[] args) throws IOException{
        new SimpleObjectClient();
    }

    public SimpleObjectClient() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9001);
        //DataInputStream in = new DataInputStream(socket.getInputStream());
        //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        try {
            Meme meme = (Meme) in.readObject();
            DankMemeViewer dankMemeViewer = new DankMemeViewer(meme.getImage(), meme.getImage().getWidth(), meme.getImage().getHeight());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        in.close();
        out.close();
        socket.close();

    }
}