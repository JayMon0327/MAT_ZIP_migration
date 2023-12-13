package SHOP.MAT_ZIP_migration.repository;

import SHOP.MAT_ZIP_migration.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
