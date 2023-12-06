package SHOP.MAT_ZIP_migration.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAndItemDto {
    private RequestProductDto productDto;
    private List<RequestItemDto> itemDtos;
}
