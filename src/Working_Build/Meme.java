package Working_Build;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Menno on 13-6-2016.
 */
public class Meme implements Comparable<Meme>, java.io.Serializable{

    private Categories categorie;
    private int rating;
    private byte[] image;

    public Meme(BufferedImage image, Categories categorie, int rating){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "PNG", os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.image = os.toByteArray();
        this.categorie = categorie;
        this.rating = rating;
    }

    //<editor-fold desc="Auto generated code hiding spot">
    @Override
    public int compareTo(Meme o) {
        return this.rating - o.rating;
    }

    public Categories getCategorie() {
        return categorie;
    }

    public void setCategorie(Categories categorie) {
        this.categorie = categorie;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    //</editor-fold>

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
