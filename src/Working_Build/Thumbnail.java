package Working_Build;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hugo on 6/13/16.
 */
public class Thumbnail implements java.io.Serializable {

    private byte[] thumbnail;
    private String path;

    public Thumbnail(ImageIcon thumbnail, String path){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage) thumbnail.getImage(), "PNG", os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.thumbnail = os.toByteArray();

        this.path = path;
    }

    public Thumbnail(String path){
        this.path = path;
    }

    public ImageIcon getThumbnail(){
        ImageIcon thumbnail2 = null;
        try {
            InputStream inputStream = new ByteArrayInputStream(thumbnail);
            thumbnail2 = new ImageIcon(ImageIO.read(inputStream));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return thumbnail2;
    }

    public String getPath(){
        return path;
    }
}
