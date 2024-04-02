package MATZIP_ver3.service;

import MATZIP_ver3.domain.Member;
import MATZIP_ver3.domain.Payment;
import MATZIP_ver3.domain.order.Order;
import MATZIP_ver3.domain.status.OrderStatus;
import MATZIP_ver3.dto.order.*;
import MATZIP_ver3.dto.order.portone.PaymentAnnotation;
import MATZIP_ver3.dto.order.portone.PaymentDetail;
import MATZIP_ver3.dto.order.portone.ResponsePortOne;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import MATZIP_ver3.repository.OrderRepository;
import MATZIP_ver3.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * 5. 주문명, 구매자, 결제금액을 응답
     */

    @Transactional
    public SuccessPayment createReservation(ResponsePortOne res, Member member) {
        // 1. 응답받은 imp_uid로 결제조회 api를 호출하여 가격 검증
        PaymentDetail verifiedPayment = verifyPrice(res.getImp_uid(), res.getAmount());

        // 2. 응답받은 포인트로 적립될 포인트 계산
        Integer addPoint = member.calculatePoint(res.getUsedPoint(), verifiedPayment.getResponse().getAmount());

        // 3. orderStatus를 Order상태로 변경
        Order order = changeOrderStatus(res.getOrderId());

        // 4. 응답받은 imp_uid로 payment 객체 생성
        Payment payment = createPayment(res.getUsedPoint(), addPoint, order, verifiedPayment);

        paymentRepository.save(payment);
        return createSuccessForm(res.getImp_uid());
    }

    public PaymentDetail verifyPrice(String impUid, int amount) {
        PaymentDetail paymentDetails = portOneService.getPaymentDetails(impUid);
        int paidAmount = paymentDetails.getResponse().getAmount();
        if (paidAmount == amount) {
            return paymentDetails;
        }
        throw new CustomException(CustomErrorCode.NOT_EQUAL_VERIFY_PRICE);
    }

    private Order changeOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_ORDER));
        order.changeOrderStatus(OrderStatus.ORDER);
        return order;
    }

    private Payment createPayment(Integer usedPoint, Integer addPoint, Order order, PaymentDetail verifiedPayment) {
        Payment payment = Payment.builder()
                .usedPoint(usedPoint)
                .addPoint(addPoint)
                .order(order)
                .amount(verifiedPayment.getResponse().getAmount())
                .impUid(verifiedPayment.getResponse().getImp_uid())
                .merchantUid(verifiedPayment.getResponse().getMerchant_uid())
                .pg_code(verifiedPayment.getResponse().getPg_provider())
                .build();
        return payment;
    }

    private SuccessPayment createSuccessForm(String impUid) {
        PaymentDetail paymentDetail = portOneService.getPaymentDetails(impUid);
        PaymentAnnotation paymentRes = paymentDetail.getResponse();
        SuccessPayment successPayment = new SuccessPayment(paymentRes.getName(), paymentRes.getAmount(),
                paymentRes.getBuyer_name());
        return successPayment;
    }

    public Page<PaymentList> getPaymentDetails(Long memberId, Pageable pageable) {
        return paymentRepository.findByOrderMemberId(memberId, pageable);
    }
}

