package entity;

import lombok.Data;

/**
 * User: louis.forite
 * Date: 02/03/15
 * Time: 22:10
 */
@Data
public class Good {
    int id;
    int maxQuantity;

    //in seconds
    int restockingTime;
}
