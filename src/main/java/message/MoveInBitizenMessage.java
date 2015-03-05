package message;

import entity.Bitizen;
import lombok.Data;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 19:05
 */
@Data
public class MoveInBitizenMessage {
    private Bitizen bitizen;
}
