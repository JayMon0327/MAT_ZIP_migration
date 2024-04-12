package MATZIP_ver3.repository;

import MATZIP_ver3.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
