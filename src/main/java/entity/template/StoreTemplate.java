package entity.template;

import entity.StoreCategory;
import lombok.Data;

/**
 * StoreTemplate class contains all the information necessary to create
 * a new store.
 * User: louis.forite
 * Date: 14/03/15
 * Time: 15:40
 */
@Data
public class StoreTemplate {
    private StoreCategory category;
    private String defaultName;
    private GoodTemplate[] goods;
}
