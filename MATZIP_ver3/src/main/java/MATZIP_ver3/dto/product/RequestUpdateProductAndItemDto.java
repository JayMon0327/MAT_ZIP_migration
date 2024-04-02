package MATZIP_ver3.dto.product;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateProductAndItemDto {

    @Valid
    private RequestProductDto productDto;
    @Valid
    private List<RequestUpdateItemDto> items;
}
