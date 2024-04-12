package SHOP.MAT_ZIP_migration.repository;

import SHOP.MAT_ZIP_migration.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
