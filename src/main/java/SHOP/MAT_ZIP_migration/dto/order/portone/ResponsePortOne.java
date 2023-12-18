package SHOP.MAT_ZIP_migration.dto.order.portone;

import SHOP.MAT_ZIP_migration.domain.order.Order;
import lombok.Data;

@Data
public class ResponsePortOne {
    private String PaymentId;
    private String imp_uid;
    private String merchant_uid;
    private Integer amount;
    private Integer usedPoint;
    private Long orderId;
}
