package message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 22:06
 */
@Data
@AllArgsConstructor
public class EndRestockMessage {
    private int goodNumber;
}
