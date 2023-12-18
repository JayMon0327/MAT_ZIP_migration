package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Payment;
import SHOP.MAT_ZIP_migration.domain.order.Order;
import SHOP.MAT_ZIP_migration.domain.status.OrderStatus;
import SHOP.MAT_ZIP_migration.dto.order.*;
import SHOP.MAT_ZIP_migration.dto.order.portone.PaymentDetail;
import SHOP.MAT_ZIP_migration.dto.order.portone.ResponsePortOne;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import SHOP.MAT_ZIP_migration.repository.OrderRepository;
import SHOP.MAT_ZIP_migration.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PaymentService {

    private static final String paymentIdHeader = "pid-";
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PortOneService portOneService;


    public PaymentForm paymentForm(RequestOrderDto dto) {
        // 현재 시간을 기준으로 paymentId 생성
        String paymentId = paymentIdHeader + System.currentTimeMillis();

        //멀티 PG 분기(db)로 관리할 수 있다. - 포트원 sdk가 필요로 하는 채널키 정보와 결제 데이터 정보를 함께 전송
        PaymentForm paymentForm = PaymentForm.builder()
                .storeId(dto.getStoreId())
                .usedPoint(dto.getUsedPoint())
                .paymentId(paymentId)
                .build();
        return paymentForm;
    }

    /**
     * STEP 2 - 결제 페이지
     * 1. 응답받은 imp_uid로 결제조회 api를 호출하여 가격 검증
     * 2. 응답받은 포인트로 멤버 포인트 계산
     * 3. 위의 응답이 true일 경우 orderStatus를 Order상태로 변경
     * 4. 응답받은 imp_uid로 payment 객체 생성
     */

    @Transactional
    public void createReservation(ResponsePortOne response, Member member) {
        // 1. 응답받은 imp_uid로 결제조회 api를 호출하여 가격 검증
        boolean isVerified = verifyPayment(response.getImp_uid(), response.getAmount());
        if (!isVerified) {
            throw new RuntimeException("서버응답 가격과 DB 가격이 다릅니다.");
        }
        // 2. 응답받은 포인트로 멤버 포인트 계산
        Integer increasePoint = member.calculatePoint(response.getUsedPoint(), response.getAmount());

        // 3. orderStatus를 Order상태로 변경
        Order order = orderRepository.findById(response.getOrderId()).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_ORDER));
        order.changeOrderStatus(OrderStatus.ORDER);

        // 4. 응답받은 imp_uid로 payment 객체 생성
        Payment payment = Payment.builder()
                .usedPoint(response.getUsedPoint())
                .addPoint(increasePoint)
                .order(order)
                .amount(response.getAmount())
                .impUid(response.getImp_uid())
                .merchantUid(response.getMerchant_uid())
                .build();

        paymentRepository.save(payment);
    }

    //액세스 토큰으로 가격 검증
    public boolean verifyPayment(String impUid, int amount) {
        PaymentDetail paymentDetails = portOneService.getPaymentDetails(impUid);
        int paidAmount = paymentDetails.getResponse().getAmount();
        return paidAmount == amount;
    }
}

