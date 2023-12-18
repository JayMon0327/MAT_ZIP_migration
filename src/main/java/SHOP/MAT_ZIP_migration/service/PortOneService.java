package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.dto.order.PaymentDetail;
import SHOP.MAT_ZIP_migration.dto.order.TokenRequest;
import SHOP.MAT_ZIP_migration.dto.order.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import okhttp3.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortOneService {

    private final OkHttpClient client = new OkHttpClient();
    private final RestTemplate restTemplate;

//    public String getAccessToken() throws IOException {
//        RequestBody body = new FormBody.Builder()
//                .add("imp_key", PortOneApiKey.RestAPIKey)
//                .add("imp_secret", PortOneApiKey.RestAPISecretKey)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://api.portone.it/login/api-key")
//                .post(body)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            // 여기에서 JSON 파싱을 통해 access_token 추출
//            String responseData = response.body().string();
//            // JSON 파싱
//            JSONParser parser = new JSONParser();
//            JSONObject jsonObject = (JSONObject) parser.parse(responseData);
//            JSONObject responseObj = (JSONObject) jsonObject.get("response");
//            String accessToken = (String) responseObj.get("access_token");
//
//            return accessToken;
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public String getAccessToken() {
        String url = "https://api.iamport.kr/users/getToken";
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setImp_Key(PortOneApiKey.RestAPIKey);
        tokenRequest.setImp_secret(PortOneApiKey.RestAPISecretKey);

        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                url,
                tokenRequest,
                TokenResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getAccess_token();
        } else {
            // 에러 시나리오 처리
            throw new RuntimeException("액세스 토큰을 가져오는 데 실패했습니다.");
        }
    }

    public PaymentDetail getPaymentDetails(String impUid) {
        String accessToken = getAccessToken(); // 액세스 토큰 가져오기
        String url = "https://api.portone.it/payments/" + impUid;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken); // Bearer 토큰 설정
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<PaymentDetail> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, PaymentDetail.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                // 에러 처리
                log.error("결제 조회 실패: " + response.getStatusCode());
                throw new RuntimeException("결제 조회 실패");
            }
        } catch (Exception e) {
            log.error("결제 조회 중 에러 발생", e);
            throw new RuntimeException("결제 조회 중 에러 발생", e);
        }
    }

//    public JSONObject getPaymentDetails(String imp_uid) throws IOException {
//        String accessToken = getAccessToken(); // 액세스 토큰 가져오기
//
//        Request request = new Request.Builder()
//                .addHeader("Authorization", "Bearer " + accessToken) // Authorization 헤더 추가
//                .url("https://api.portone.it/v1/payments/" + imp_uid) // 결제 내역 조회 API URL
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected code " + response);
//            }
//
//            String responseData = response.body().string();
//            JSONParser parser = new JSONParser();
//            JSONObject jsonObject = (JSONObject) parser.parse(responseData);
//            log.info("결제조회결과 : "+jsonObject);
//            return jsonObject; // 결제 내역 정보 반환
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
