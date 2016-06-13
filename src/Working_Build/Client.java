package Working_Build;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

/**
 * Created by hugo on 5/18/16.
 */
public class Client {

    private DataInputStream in;
    private DataOutputStream out;
    protected Socket socket;
    private Gui gui;

    public static void main(String s[]) throws IOException{
        new Client();
    }

    public Client() throws IOException{
        socket = new Socket("127.0.0.1", 9001);
        gui = new Gui(this);
    }
    public DataOutputStream getOut() {
        return out;
    }
}
