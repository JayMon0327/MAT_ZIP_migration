package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.dto.product.RequestReviewDto;
import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@Tag(name = "05.Review")
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    @Operation(summary = "리뷰 등록", description = "리뷰 등록")
    public ResponseDto<Integer> save(@Valid @ModelAttribute RequestReviewDto dto,
                                     @AuthenticationPrincipal PrincipalDetails principal) {
        reviewService.saveReview(dto, principal.getMember());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/review/{id}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제")
    public ResponseDto<Integer> delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
