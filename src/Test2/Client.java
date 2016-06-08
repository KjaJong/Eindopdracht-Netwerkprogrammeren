package Test2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.file.Path;

/**
 * Created by hugo on 5/18/16.
 */
public class Client {

    private static DataInputStream in;
    private static DataOutputStream out;

    public static void main(String s[]) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9001);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        //int aNumber = Integer.parseInt(JOptionPane.showInputDialog("Gimme number"));
        //out.writeDouble(aNumber);

        receiveImage();

        out.close();
        in.close();
        socket.close();

    }

    private static void receiveImage() throws IOException {
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
}
