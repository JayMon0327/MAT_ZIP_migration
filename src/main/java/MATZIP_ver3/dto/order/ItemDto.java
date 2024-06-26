package MATZIP_ver3.dto.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

    @NotNull
    private Long itemId;

    @NotBlank
    @Length(min = 4, max = 20)
    private String itemName;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer count;
}
