package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.dto.product.RequestReviewDto;
import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.service.ReviewService;
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
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseDto<Integer> save(@Valid @ModelAttribute RequestReviewDto dto,
                                     @AuthenticationPrincipal PrincipalDetails principal) {
        reviewService.saveReview(dto, principal.getMember());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/review/{id}")
    public ResponseDto<Integer> delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
