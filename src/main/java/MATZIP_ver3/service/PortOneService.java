package MATZIP_ver3.service;

import MATZIP_ver3.dto.order.portone.PaymentDetail;
import MATZIP_ver3.dto.order.portone.ReqCancelPayment;
import MATZIP_ver3.dto.order.portone.TokenRequest;
import MATZIP_ver3.dto.order.portone.TokenResponse;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PortOneService {

    private final WebClient webClient;

    public PortOneService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.iamport.kr").build();
    }


    /**
     * 액세스 토큰 발급
     */
    public String getAccessToken() {
        TokenRequest tokenRequest = new TokenRequest(PortOneApiKey.RestAPIKey, PortOneApiKey.RestAPISecretKey);

        TokenResponse tokenResponse = webClient.post()
                .uri("/users/getToken")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(tokenRequest), TokenRequest.class)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();

        if (tokenResponse != null && tokenResponse.getResponse() != null) {
            return tokenResponse.getResponse().getAccess_token();
        } else {
            throw new CustomException(CustomErrorCode.FAIL_BRING_ACCESS_TOKEN);
        }
    }

    /**
     * 액세스 토큰으로 결제 단건 조회
     */
    public PaymentDetail getPaymentDetails(String impUid) {
        String accessToken = getAccessToken();

        PaymentDetail paymentDetail = webClient.get()
                .uri("/payments/" + impUid)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(PaymentDetail.class)
                .block();

        if (paymentDetail != null) {
            return paymentDetail;
        } else {
            throw new CustomException(CustomErrorCode.FAIL_FIND_PAYMENT_DETAIL);
        }
    }

    /**
     * 결제 취소
     */
    public void cancelPayment(String impUid, String merchantUid, Integer amount) {
        String accessToken = getAccessToken();

        ReqCancelPayment reqCancelPayment = ReqCancelPayment.builder()
                .imp_uid(impUid)
                .merchant_uid(merchantUid)
                .checksum(amount)
                .build();

        PaymentDetail paymentDetail = webClient.post()
                .uri("/payments/cancel")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reqCancelPayment)
                .retrieve()
                .bodyToMono(PaymentDetail.class)
                .block();
    }
}