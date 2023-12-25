package SHOP.MAT_ZIP_migration.repository;

import SHOP.MAT_ZIP_migration.domain.Payment;
import SHOP.MAT_ZIP_migration.dto.order.PaymentList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByImpUid(String impUid);

    @Query("SELECT new SHOP.MAT_ZIP_migration.dto.order.PaymentList(p.impUid, p.order.id, p.amount, p.createDate) FROM Payment p WHERE p.order.member.id = :memberId")
    Page<PaymentList> findByOrderMemberId(Long memberId, Pageable pageable);

    Payment findByOrderId(Long orderId);
}
