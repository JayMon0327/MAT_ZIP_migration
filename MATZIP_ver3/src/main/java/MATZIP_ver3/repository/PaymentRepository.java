package MATZIP_ver3.repository;

import MATZIP_ver3.domain.Payment;
import MATZIP_ver3.dto.order.PaymentList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByImpUid(String impUid);

    @Query("SELECT new MATZIP_ver3.dto.order.PaymentList(p.impUid, p.order.id, p.amount, p.createDate) FROM Payment p WHERE p.order.member.id = :memberId")
    Page<PaymentList> findByOrderMemberId(Long memberId, Pageable pageable);

    Payment findByOrderId(Long orderId);
}
