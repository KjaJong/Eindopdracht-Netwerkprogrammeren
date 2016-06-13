package Working_Build;

import java.util.Comparator;

/**
 * Created by Menno on 13-6-2016.
 */
public class SortByCategorie implements Comparator<Meme> {

    @Override
    public int compare(Meme o1, Meme o2) {
        return o1.getCategorie().compareTo(o2.getCategorie());
    }
}
