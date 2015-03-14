package entity.template;

import entity.StoreCategory;
import utils.JsonFileReader;

import java.util.*;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 22:34
 */
public class StoreTemplateCollection {

    public static final Map<StoreCategory, List<StoreTemplate>> mapStoreCategoryToStores;
    /**
     * Keep a track of already used stores
     */
    public static final List<StoreTemplate> alreadyUsed;

    static {
        mapStoreCategoryToStores = new HashMap<>(StoreCategory.values().length);
        alreadyUsed = new ArrayList<>();

        List<StoreTemplate> stores = JsonFileReader.loadStoreTemplates();
        /**
         * Rearrange stores by store category
         */
        for (StoreTemplate store : stores) {
            if (mapStoreCategoryToStores.containsKey(store.getCategory())) {
                mapStoreCategoryToStores.get(store.getCategory()).add(store);
            } else {
                List<StoreTemplate> storeList = new ArrayList<>();
                storeList.add(store);
                mapStoreCategoryToStores.put(store.getCategory(), storeList);
            }
        }
    }

    /**
     * Pick a store in the collection of the given type
     * and remove it from the store collection so its is not available anymore
     *
     * @param storeCategory the category of stores to get
     * @return a random store
     */
    public static StoreTemplate pickRandomStoreInformation(StoreCategory storeCategory) {
        List<StoreTemplate> stores = mapStoreCategoryToStores.get(storeCategory);
        StoreTemplate storeTemplate = stores.remove(new Random().nextInt(stores.size() + 1));
        alreadyUsed.add(storeTemplate);
        return storeTemplate;
    }
}
