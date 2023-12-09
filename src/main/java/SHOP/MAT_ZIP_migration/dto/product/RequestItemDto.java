package SHOP.MAT_ZIP_migration.dto.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestItemDto {

    @NotBlank(message = "상품 이름을 입력해주세요.")
    @Length(min = 5, max = 20, message = "상품이름은 5자 이상 20자 이하 제한입니다.")
    private String name;

    @NotBlank(message = "상품 가격을 입력해주세요.")
    @Min(value = 1000, message = "가격은 1000원 이상이어야 합니다.")
    @Max(value = 1000000, message = "가격은 1000000원 이하이어야 합니다.")
    private int price;

    @NotBlank(message = "상품 수량을 입력해주세요.")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    @Max(value = 100, message = "수량은 100개 이하이어야 합니다.")
    private int stock;
}
