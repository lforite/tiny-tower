package message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * User: louis.forite
 * Date: 12/03/15
 * Time: 21:01
 */
@Data
@AllArgsConstructor
public class ReportMoneyMessage {
    private Integer amount;
}
