package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.dto.order.portone.PaymentDetail;
import SHOP.MAT_ZIP_migration.dto.order.portone.TokenRequest;
import SHOP.MAT_ZIP_migration.dto.order.portone.TokenResponse;
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
            log.info("액세스토큰" + tokenResponse);
            return tokenResponse.getResponse().getAccess_token();
        } else {
            String errorMessage = (tokenResponse != null) ? tokenResponse.getMessage() : "액세스 토큰을 가져오는 데 실패했습니다.";
            throw new RuntimeException(errorMessage);
        }
    }

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
            throw new RuntimeException("결제 조회 실패");
        }
    }
}