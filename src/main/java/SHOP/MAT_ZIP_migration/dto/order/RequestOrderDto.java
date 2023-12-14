package SHOP.MAT_ZIP_migration.dto.order;

import SHOP.MAT_ZIP_migration.domain.order.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderDto {

    private List<ItemDto> itemDtos;
    private Address address;
    private Integer usedPoint;
    private Integer totalPrice;
    private String storeId;
}
