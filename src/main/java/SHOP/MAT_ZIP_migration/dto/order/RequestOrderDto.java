package SHOP.MAT_ZIP_migration.dto.order;

import SHOP.MAT_ZIP_migration.domain.order.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderDto {

    @NotNull
    private List<ItemDto> itemDtos;

    @NotNull
    private Address address;

    @NotNull
    private Integer usedPoint;

    @NotNull
    private Integer totalPrice;

    @NotBlank
    private String storeId;
}
