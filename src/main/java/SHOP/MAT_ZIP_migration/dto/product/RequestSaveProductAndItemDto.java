package SHOP.MAT_ZIP_migration.dto.product;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSaveProductAndItemDto {

    @Valid
    private RequestProductDto productDto;
    @Valid
    private List<RequestSaveItemDto> items;
}
