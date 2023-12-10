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

    @NotBlank
    @Length(min = 5, max = 20)
    private String name;

    @NotNull
    @Min(1000)
    @Max(1000000)
    private Integer price;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer stock;
}
