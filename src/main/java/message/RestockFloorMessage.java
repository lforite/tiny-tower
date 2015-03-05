package message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 18:55
 */
@Data
@AllArgsConstructor
public class RestockFloorMessage {
    private int floorNumber;
    private Integer goodNumber;
}
