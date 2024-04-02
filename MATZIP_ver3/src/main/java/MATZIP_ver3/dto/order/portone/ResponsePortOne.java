package MATZIP_ver3.dto.order.portone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponsePortOne {
    private String PaymentId;
    private String imp_uid;
    private String merchant_uid;
    private Integer amount;
    private Integer usedPoint;
    private Long orderId;
}
