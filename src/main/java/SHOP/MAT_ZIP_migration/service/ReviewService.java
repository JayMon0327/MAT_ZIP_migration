package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.domain.Review;
import SHOP.MAT_ZIP_migration.domain.ReviewImage;
import SHOP.MAT_ZIP_migration.dto.RequestReviewDto;
import SHOP.MAT_ZIP_migration.repository.ProductRepository;
import SHOP.MAT_ZIP_migration.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ProductRepository productRepository;
    private final FileStore fileStore;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void saveReview(RequestReviewDto reviewDto, Member member) throws IOException {
        Product product = productRepository.findById(reviewDto.getProductId()).orElseThrow();
        Review review = Review.builder()
                .product(product)
                .member(member)
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .build();
        List<ReviewImage> reviewImages = fileStore.storeReviewFiles(reviewDto.getImageFiles());
        reviewImages.forEach(review::addImage);
        reviewRepository.save(review);
    }
}
