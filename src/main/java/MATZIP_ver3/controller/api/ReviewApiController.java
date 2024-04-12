package MATZIP_ver3.controller.api;

import MATZIP_ver3.config.auth.PrincipalDetails;
import MATZIP_ver3.dto.product.RequestReviewDto;
import MATZIP_ver3.dto.ResponseDto;
import MATZIP_ver3.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
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
