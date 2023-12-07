package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.domain.Review;
import SHOP.MAT_ZIP_migration.dto.RequestReviewDto;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import SHOP.MAT_ZIP_migration.repository.ProductRepository;
import SHOP.MAT_ZIP_migration.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @DisplayName("리뷰 작성 통합테스트")
    @Test
    public void saveReviewTest() throws IOException {
        Member member = memberRepository.findById(1L).orElseThrow();
        Product product = productRepository.findById(1L).orElseThrow();

        // 리뷰 DTO 생성
        RequestReviewDto reviewDto = new RequestReviewDto();
        reviewDto.setMemberId(member.getId());
        reviewDto.setProductId(product.getId());
        reviewDto.setContent("테스트 리뷰 내용");
        reviewDto.setRating(5);

        // Mock 이미지 파일 추가
        MultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());
        reviewDto.setImageFiles(List.of(mockFile));

        // 테스트 실행
        reviewService.saveReview(reviewDto, member);

        // 검증
        List<Review> reviews = reviewRepository.findByProduct(product);
        assertThat(reviews).isNotEmpty();
        assertThat(reviews.get(0).getContent()).isEqualTo("adsf");
        assertThat(reviews.get(0).getRating()).isEqualTo(3);
        assertThat(reviews.get(0).getReviewImages()).isNotEmpty();
    }
}
