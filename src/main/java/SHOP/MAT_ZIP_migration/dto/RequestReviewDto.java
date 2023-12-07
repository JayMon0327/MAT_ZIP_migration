package SHOP.MAT_ZIP_migration.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    private Long memberId;
    private Long productId;

    @NotBlank(message = "내용은 빈칸일 수 없습니다.")
    private String content;

    @Min(value = 1, message = "평점은 1점 이상입니다.")
    @Max(value = 5, message = "평점은 5점까지 입니다.")
    private int rating;

    private List<MultipartFile> imageFiles = new ArrayList<>();
}
