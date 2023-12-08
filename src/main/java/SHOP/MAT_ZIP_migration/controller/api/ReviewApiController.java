package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.dto.RequestReviewDto;
import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/product/{id}/review")
    public ResponseDto<Integer> replySave(@Valid @ModelAttribute RequestReviewDto dto,
                                          @AuthenticationPrincipal PrincipalDetails principal) {
        reviewService.saveReview(dto, principal.getMember());
        log.info("댓글작성요청");
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
