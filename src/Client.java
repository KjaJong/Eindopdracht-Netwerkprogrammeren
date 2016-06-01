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
    public static void main(String s[]) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9001);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        //int aNumber = Integer.parseInt(JOptionPane.showInputDialog("Gimme number"));
        //out.writeDouble(aNumber);
        String path = JOptionPane.showInputDialog("Gimme image path");
        System.out.println(path);

        //YES, YES TERRY! SEND, THE IMAGE!!!
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File(path));

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("I'm firin' mah byte stream!");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", os);
        os.flush();
        byte[] bytes = os.toByteArray();

        System.out.println(bytes.length);
        out.writeInt(bytes.length);

        for(int i = 0; i < bytes.length; i++) {
            out.writeByte(bytes[i]);
        }

        out.close();
        in.close();
        socket.close();

    }
}
