package SHOP.MAT_ZIP_migration.dto.order;

import SHOP.MAT_ZIP_migration.domain.order.Order;
import SHOP.MAT_ZIP_migration.domain.order.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentForm {

    private String[] pg_code;
    private String storeId;
    private String channelKey;
    private String naverPayChannelKey;
    private String kakaoPayChannelKey;
    private String paymentId;

    private Order order;
    private List<OrderItem> orderItems;
}
