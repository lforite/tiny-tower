package entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 19:58
 */
@Data
public class Bitizen {
    private String firstName;
    private String lastName;

    private BitizenMood mood;

    private Map<StoreCategory, Integer> mapCategoryToStrength;

    private String idealJob;

    public Bitizen() {
        Random random = new Random();
        mapCategoryToStrength = new HashMap<>(5);
        mapCategoryToStrength.put(StoreCategory.CREATIVE, random.nextInt(10));
        mapCategoryToStrength.put(StoreCategory.FOOD, random.nextInt(10));
        mapCategoryToStrength.put(StoreCategory.RECREATION, random.nextInt(10));
        mapCategoryToStrength.put(StoreCategory.RETAIL, random.nextInt(10));
        mapCategoryToStrength.put(StoreCategory.SERVICE, random.nextInt(10));
    }

}
