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
public class ResponseOrderForm {

    @NotNull
    private List<ItemDto> items;
    @NotBlank
    private String storeId;
    @NotNull
    private Integer totalPrice;
    @NotNull
    private Address address;
}
