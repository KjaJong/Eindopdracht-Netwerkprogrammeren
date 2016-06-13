package Working_Build;

import java.util.Comparator;

/**
 * Created by Menno on 13-6-2016.
 */
public class SortByRating implements Comparator<Meme> {

    @Override
    public int compare(Meme o1, Meme o2) {
        if(o1.getRating() > o2.getRating()){
            return 1;
        }
        else if(o1.getRating() == o2.getRating()){
            return 0;
        }
        else{
            return -1;
        }
    }
}
