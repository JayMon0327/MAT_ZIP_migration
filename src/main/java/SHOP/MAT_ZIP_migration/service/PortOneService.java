package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.dto.order.portone.PaymentDetail;
import SHOP.MAT_ZIP_migration.dto.order.portone.TokenRequest;
import SHOP.MAT_ZIP_migration.dto.order.portone.TokenResponse;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
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
}