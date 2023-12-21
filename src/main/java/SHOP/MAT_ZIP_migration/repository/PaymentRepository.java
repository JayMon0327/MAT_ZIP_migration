package SHOP.MAT_ZIP_migration.repository;

import SHOP.MAT_ZIP_migration.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByImpUid(String impUid);
    Page<Payment> findByOrderMemberId(Long memberId, Pageable pageable);
}
