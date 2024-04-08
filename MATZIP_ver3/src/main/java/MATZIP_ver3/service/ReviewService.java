package MATZIP_ver3.service;

import MATZIP_ver3.domain.Member;
import MATZIP_ver3.domain.Product;
import MATZIP_ver3.domain.Review;
import MATZIP_ver3.domain.ReviewImage;
import MATZIP_ver3.dto.product.RequestReviewDto;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import MATZIP_ver3.repository.OrderRepository;
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
    private final OrderRepository orderRepository;
    private final FileStore<ReviewImage> fileStore;

    public ReviewService(ProductRepository productRepository, ReviewRepository reviewRepository,
                         @Qualifier("reviewFileStore") FileStore<ReviewImage> fileStore, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.fileStore = fileStore;
    }

    @Transactional
    public boolean saveReview(RequestReviewDto dto, Member member) {
        if (!hasPurchasedProduct(member, dto.getProductId())) {
            throw new CustomException(CustomErrorCode.NOT_ORDER_MEMBER);
        }
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_PRODUCT));
        Review review = Review.builder()
                .product(product)
                .member(member)
                .content(dto.getContent())
                .rating(dto.getRating())
                .build();
        List<ReviewImage> reviewImages = fileStore.storeFiles(dto.getImageFiles());
        reviewImages.forEach(review::addImage);
        reviewRepository.save(review);
        return true;
    }

    @Transactional(readOnly = true)
    public boolean hasPurchasedProduct(Member member, Long productId) {
        return orderRepository.existsByMemberAndProductId(member, productId);
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
