package MATZIP_ver3.repository;

import MATZIP_ver3.domain.Product;
import MATZIP_ver3.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProduct(Product product);
}
