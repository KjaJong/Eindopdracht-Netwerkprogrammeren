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
        //in = new DataInputStream(socket.getInputStream());
        //out = new DataOutputStream(socket.getOutputStream());
        gui = new Gui(this);
        //int aNumber = Integer.parseInt(JOptionPane.showInputDialog("Gimme number"));
        //out.writeDouble(aNumber);

        //TODO gui sending requests
        //receiveImage();

        //out.close();
        //in.close();
        //socket.close();

    }

    private void receiveImage() throws IOException {
        int length = in.readInt();
        byte[] bytes = new byte[length];
        for(int i = 0; i < length; i++){
            bytes[i] = (in.readByte());
        }

        BufferedImage bufferedImage=null;
        try {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        if(bufferedImage != null){
            DankMemeViewer dankMemeViewer = new DankMemeViewer(bufferedImage, bufferedImage.getWidth()+10, bufferedImage.getHeight()+20);
        } else {
            System.out.println("Got nothin' cap'n");
        }
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }
}
