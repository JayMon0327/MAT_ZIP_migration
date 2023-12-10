package SHOP.MAT_ZIP_migration.dto.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestReviewDto {
    @NotNull(message = "{member.notNull}")
    private Long memberId;
    @NotNull(message = "{product.notNull}")
    private Long productId;

    @NotBlank(message = "{content.notBlank}")
    private String content;

    @NotNull(message = "{review.notNull}")
    @Min(value = 1, message = "{rating.min}")
    @Max(value = 5, message = "{rating.max}")
    private Integer rating;

    private List<MultipartFile> imageFiles = new ArrayList<>();
}
