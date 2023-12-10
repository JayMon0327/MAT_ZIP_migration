package SHOP.MAT_ZIP_migration.dto.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestItemDto {

    @NotBlank(message = "{name.notBlank}")
    @Length(min = 5, max = 20, message = "{name.length}")
    private String name;

    @NotNull(message = "{price.notNull}")
    @Min(value = 1000, message = "{price.min}")
    @Max(value = 1000000, message = "{price.max}")
    private Integer price;

    @NotNull(message = "{stock.notNull}")
    @Min(value = 1, message = "{stock.min}")
    @Max(value = 100, message = "{stock.max}")
    private Integer stock;
}
