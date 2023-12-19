package SHOP.MAT_ZIP_migration.dto.order;

import SHOP.MAT_ZIP_migration.domain.order.Order;
import SHOP.MAT_ZIP_migration.domain.order.OrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    private String storeId;

    @NotBlank
    private String paymentId;

    @NotNull
    private Integer finalPrice;

    @NotNull
    private Integer usedPoint;

    @NotNull
    private Order order;

    private List<OrderItem> orderItems;
}
