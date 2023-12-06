package SHOP.MAT_ZIP_migration.dto.product;

import SHOP.MAT_ZIP_migration.domain.Member;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "제목은 빈칸일 수 없습니다.")
    @Length(min = 10, max = 30, message = "제목은 10자이상 30자 미만입니다.")
    private String title;

    @NotBlank(message = "내용은 빈칸일 수 없습니다.")
    private String description;

    private Member member;

    private List<MultipartFile> imageFiles = new ArrayList<>();
}
