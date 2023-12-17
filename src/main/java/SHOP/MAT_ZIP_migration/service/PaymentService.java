package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Payment;
import SHOP.MAT_ZIP_migration.domain.order.Order;
import SHOP.MAT_ZIP_migration.dto.order.PaymentForm;
import SHOP.MAT_ZIP_migration.dto.order.RequestOrderDto;
import SHOP.MAT_ZIP_migration.dto.order.ResPayment;
import SHOP.MAT_ZIP_migration.dto.order.ResponsePortOne;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import SHOP.MAT_ZIP_migration.repository.OrderRepository;
import SHOP.MAT_ZIP_migration.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PaymentService {

    private static final String paymentIdHeader = "pid-";
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;


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

    @Transactional
    public ResPayment createReservation(ResponsePortOne response, Member member) {
        //응답 header 생성
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        JSONObject responseObj = new JSONObject();

        //웹훅 우선순위 설정에 따라 웹훅으로 DB결과를 반영하여 콜백은 DB의 결과를 조회하여 프론트로 전달한다.
        try {
            //회원 포인트 처리, 검증
            Integer increasePoint = member.calculatePoint(response.getUsedPoint(), response.getAmount());
            Order order = orderRepository.findById(response.getOrderId()).orElseThrow(
                    () -> new CustomException(CustomErrorCode.NOT_FOUND_ORDER));

            Payment payment = Payment.builder()
                    .usedPoint(response.getUsedPoint())
                    .addPoint(increasePoint)
                    .order(order)
                    .amount(response.getAmount())
                    .imp_uid(response.getImp_uid())
                    .merchant_uid(response.getMerchant_uid())
                    .build();

            paymentRepository.save(payment);
            responseObj.put("status", HttpStatus.OK);
            responseObj.put("message", "결제 성공");

        } catch (Exception e) {
            responseObj.put("status", "error");
            responseObj.put("message", "결제 처리 실패.");
        }
        return new ResPayment(responseObj, responseHeaders);
    }
}
