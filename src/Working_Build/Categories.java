package Working_Build;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Menno on 13-6-2016.
 */
public enum Categories {
    DANK, ANIMU, FUNNY, NSFW, REDDIT_MADE, FOUR_CHAN, EIGHT_CHAN, DARK_HUMOR, LINDIE, RNSFW, DASH_B, WAIFU, TROLL;

private static final List<Categories> CATEGORIE = Collections.unmodifiableList(Arrays.asList(values()));
private static final int SIZE = CATEGORIE.size();
private static final Random RANDOM = new Random();

public static Categories randomCategorie(){
    return CATEGORIE.get(RANDOM.nextInt(SIZE));
}}
