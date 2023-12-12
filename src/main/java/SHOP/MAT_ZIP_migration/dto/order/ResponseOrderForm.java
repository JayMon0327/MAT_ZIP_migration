package SHOP.MAT_ZIP_migration.dto.order;

import SHOP.MAT_ZIP_migration.domain.order.Address;
import SHOP.MAT_ZIP_migration.domain.order.Delivery;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOrderForm {

    private List<ItemDto> items;
    private String sellerName;
    private Integer totalPrice;
    private Address address;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemDto {

        @NotBlank
        @Length(min = 4, max = 20)
        private String itemName;

        @NotNull
        @Min(1)
        @Max(100)
        private Integer count;
    }
}
