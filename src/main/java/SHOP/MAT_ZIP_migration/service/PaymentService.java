package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.dto.order.PaymentForm;
import SHOP.MAT_ZIP_migration.dto.order.RequestOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PaymentService {

    private static final String toss = "tosspayments";
    private static final String ksnet = "ksnet";
    private static final String paymentId = "pid -" + System.currentTimeMillis();

    public PaymentForm requestPaymentForm(RequestOrderDto dto) {
        //멀티 PG 분기(db)로 관리할 수 있다. - 포트원 sdk가 필요로 하는 채널키 정보와 결제 데이터 정보를 함께 전송
        String[] pg_code = {toss, ksnet};
        String[] channelKey = {PaymentKey.CHANNEL_KEY_TOSS, PaymentKey.CHANNEL_KEY_KSNET};
        long selected_pg = dto.getItemDtos().get(0).getItemId() % 2;

        PaymentForm paymentForm = PaymentForm.builder()
                .pg_code(pg_code)
                .storeId(dto.getStoreId())
                .channelKey(channelKey[(int) selected_pg])
                .naverPayChannelKey(PaymentKey.NAVER_PAY_CHANNEL_KEY)
                .kakaoPayChannelKey(PaymentKey.KAKAO_PAY_CHANNEL_KEY)
                .paymentId(paymentId)
                .build();
        return paymentForm;
    }
}
