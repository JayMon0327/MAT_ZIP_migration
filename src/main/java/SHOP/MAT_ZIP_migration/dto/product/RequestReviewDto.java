package SHOP.MAT_ZIP_migration.dto.product;

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
    @NotBlank(message = "미로그인 사용자 접근오류")
    private Long memberId;
    @NotBlank(message = "게시판 ID 미 전달 오류")
    private Long productId;

    @NotBlank(message = "내용은 빈칸일 수 없습니다.")
    private String content;

    @NotBlank(message = "평점 미입력 오류")
    @Min(value = 1, message = "평점은 1점 이상입니다.")
    @Max(value = 5, message = "평점은 5점까지 입니다.")
    private int rating;

    private List<MultipartFile> imageFiles = new ArrayList<>();
}
