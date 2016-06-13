package Working_Build;

<<<<<<< HEAD
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Menno on 13-6-2016.
 */
public class Meme implements Comparable<Meme>{

    private Image memePicture;
    private ArrayList<Categories> categories = new ArrayList<>();
    private int rating;

    public Meme(Image memePicture, Categories categorie, int rating){
        this.memePicture = memePicture;
        categories.add(categorie);
        this.rating = rating;
    }

    protected void setAnotherCategorie(Categories categorie){
        if(!categories.contains(categorie)){
            categories.add(categorie);
        }
        else{
            System.out.println("Yeah nope. Already got that one.");
        }

    }

    protected void removeACategorie(Categories categorie){
        if(categories.contains(categorie)){
            categories.remove(categorie);
        }
        else{
            System.out.println("Hey, stop removing non-existent stuff!");
        }
    }
    //<editor-fold desc="Auto generated code hiding spot">
    @Override
    public int compareTo(Meme o) {
        return 0;
    }

    public Image getMemePicture() {
        return memePicture;
    }

    public void setMemePicture(Image memePicture) {
        this.memePicture = memePicture;
    }

    public ArrayList<Categories> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categories> categories) {
        this.categories = categories;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    //</editor-fold>
=======
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
>>>>>>> 5a95056b6993bf05d11da4a5a986fd38b8fb8a4b
}
