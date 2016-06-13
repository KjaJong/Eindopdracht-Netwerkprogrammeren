package Working_Build;

/**
 * Created by hugo on 6/13/16.
 */
public class SimpleObject implements java.io.Serializable{

    private int dankness;
    private String theMeaningOfLife;

    public SimpleObject(int dankness){
        this.dankness = dankness;
        theMeaningOfLife = "A E S T H E T I C";
    }

    public int getDankNess(){
        return dankness;
    }

    public String getTheMeaningOfLife(){
        return theMeaningOfLife;
    }
}