package Working_Build;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hugo on 6/13/16.
 */
public class Meme implements java.io.Serializable {

    private byte[] image;

    public Meme(BufferedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "PNG", os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.image = os.toByteArray();
    }

    public BufferedImage getImage(){
        BufferedImage image2 = null;
        try {
            InputStream inputStream = new ByteArrayInputStream(image);
            image2 = ImageIO.read(inputStream);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return image2;
    }
}
