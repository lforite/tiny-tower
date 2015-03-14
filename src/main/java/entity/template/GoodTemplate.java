package entity.template;

import lombok.Data;

/**
 * User: louis.forite
 * Date: 02/03/15
 * Time: 22:10
 */
@Data
public class GoodTemplate {
    private String name;
    private Integer maxQuantity;
    private Long restockingTime;
}