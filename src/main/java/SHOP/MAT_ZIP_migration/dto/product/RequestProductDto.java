package SHOP.MAT_ZIP_migration.dto.product;

import SHOP.MAT_ZIP_migration.domain.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestProductDto {

    @NotBlank
    @Length(min = 5, max = 30)
    private String title;

    @NotBlank
    @Length(min = 10, max = 1000)
    private String description;

    @Builder.Default
    private List<MultipartFile> imageFiles = new ArrayList<>();
}
