package Working_Build;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Menno on 13-6-2016.
 */
public class Meme implements Comparable<Meme>, java.io.Serializable{

    private ArrayList<Categories> categories = new ArrayList<>();
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
