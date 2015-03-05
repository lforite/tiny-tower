package message;

import entity.FloorType;
import entity.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 18:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildFloorMessage {
    //ie. apartment, store
    private FloorType floorType;
    private StoreCategory storeCategory;
}
