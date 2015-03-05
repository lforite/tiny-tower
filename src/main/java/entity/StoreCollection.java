package entity;

import java.util.HashMap;
import java.util.Map;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 22:34
 */
public class StoreCollection {

    private StoreCollection() {
    }


    public static final Map<StoreCategory, Store> stores = new HashMap<>();

    public Store getRandomFloorOfType(StoreCategory storeCategory) {

        return null;
    }

    public static class Store {
        private String name;
    }
}
