package Deprecated;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hugo on 5/18/16.
 */
public class Server {

    private static DataInputStream in;
    private static byte[] data;

    public static void main(String s[]) throws IOException {
        ServerSocket server = new ServerSocket(9001);
        Socket socket = server.accept();
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        int length = in.readInt();
        byte[] bytes = new byte[length];
        for(int i = 0; i < length; i++){
            bytes[i] = (in.readByte());
            System.out.println(bytes[i]);
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