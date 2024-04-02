package MATZIP_ver3.dto.order;

import MATZIP_ver3.domain.order.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
