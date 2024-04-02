package MATZIP_ver3.service;

import MATZIP_ver3.domain.Member;
import MATZIP_ver3.domain.Product;
import MATZIP_ver3.domain.Review;
import MATZIP_ver3.domain.ReviewImage;
import MATZIP_ver3.dto.product.RequestReviewDto;
import MATZIP_ver3.repository.ProductRepository;
import MATZIP_ver3.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final FileStore<ReviewImage> fileStore;

    public ReviewService(ProductRepository productRepository, ReviewRepository reviewRepository,
                         @Qualifier("reviewFileStore") FileStore<ReviewImage> fileStore) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.fileStore = fileStore;
    }

    @Transactional
    public void saveReview(RequestReviewDto reviewDto, Member member) {
        Product product = productRepository.findById(reviewDto.getProductId()).orElseThrow();
        Review review = Review.builder()
                .product(product)
                .member(member)
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .build();
        List<ReviewImage> reviewImages = fileStore.storeFiles(reviewDto.getImageFiles());
        reviewImages.forEach(review::addImage);
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
