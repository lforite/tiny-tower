package message;

import entity.Bitizen;
import lombok.Data;

/**
 * User: louis.forite
 * Date: 24/02/15
 * Time: 23:01
 */
@Data
public class IncomingBitizenMessage {
    private Bitizen bitizen;
    private int floorNumber;

    public IncomingBitizenMessage(int floorNumber) {
        this.bitizen = new Bitizen();
        this.floorNumber = floorNumber;
    }
}
