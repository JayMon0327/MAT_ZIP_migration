package MATZIP_ver3.repository;

import MATZIP_ver3.domain.Member;
import MATZIP_ver3.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 특정 회원이 특정 상품에 대한 구매 이력이 있는지 확인
    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.orderItems oi JOIN oi.item i WHERE o.member = :member AND i.product.id = :productId")
    boolean existsByMemberAndProductId(@Param("member") Member member, @Param("productId") Long productId);

}

