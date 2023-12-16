package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.service.PaymentService;
import SHOP.MAT_ZIP_migration.service.PortOneApiKey;
import SHOP.MAT_ZIP_migration.service.PortOneClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.coyote.Request;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping("/payment/complete")
    public ResponseDto<Integer> callback_receive(RequestBody PortOne entity) {
        //결제 결과로 수신되는 콜백

        //응답 header 생성
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        JSONObject responseObj = new JSONObject();

        try {
            String txId = entity.getTxId();
            String paymentId = entity.getPaymentId();
            String code = entity.getCode();
            String message = entity.getMessage();

            System.out.println("---callback receive ---");
            System.out.println("------------------------");
            System.out.println("txId : "txId);
            System.out.println("paymentId :" + paymentId);
            System.out.println("code: " + code);

            //웹훅 우선순위 설정에 따라 웹훅으로 DB결과를 반영하여 콜백은 DB의 결과를 조회하여 프론트로 전달한다.
            Payment payment = this.paymentService.findPaymentByMerchantUid(paymentId);
            String status = "fail";
            String fail_reason = "결제에 실패하였습니다.";
            if (payment != null) {
                status = payment.getStatus();
                fail_reason = payment.getFailReason();
            }
            responseObj.put("status", status);
            responseObj.put("fail_reason", fail_reason);

        } catch (Exception e) {
            e.printStackTrace();
            responseObj.put("status", "fail");
            responseObj.put("fail_reason", "관리자에게 문의해 주세요.");
        }

        return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpStatus.OK);

    }

    //웹훅 수신 처리
    @PostMapping("/payment/webhook")
    public ResponseEntity<?> webhook_receive(@RequestBody Portone entity) {

        //응답 header 생성
        //응답 header 생성
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-type", "application/json; charset=UTF-8");
        JSONObject responseObj = new JSONObject();

        try {
            System.out.println("----webhook receive-----");
            System.out.println("-------------------------");
            System.out.println("txId : " + txId);
            System.out.println("paymentId: " + paymentId);

            String status = doResult(entity);

        } catch (Exception e) {
            e.printStackTrace();
            responseObj.put("status", "결제실패: 관리자에게 문의해주세요.");
        }
        return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpStatus.OK);
    }

    //공통처리
    private String doResult(Portone entity) {

        String status = "";

        try {
            String txId = entity.getTx.id();
            String paymentId = entity.getPayment_id();
            String code = entity.getCode();
            String message = entity.getMessage();

            if (paymentId != null) {
                // STEP 5
                JSONObject json = new JSONObject();
                String apiKey = PortOneApiKey.RestAPIKey, PortOne
                json.put("apiKey", this.apiKey);
                okhttp3.RequestBody body = okhttp3.RequestBody.create(json.toJSONString(), this.mediaType);

                //결제 조회를 위한 access_token 발급 발급 후 하루의 유효기간
                Request request = new Request.Builder()
                        .url("https://api.portone.it/login/api-key")
                        .post(body)
                        .build();

                OKHttpClient okClient = new OKHttpClient(request);
                int http_code = okClient.doUsingHttp();
                System.out.println(http_code + ":" + okClient.getResponseBody());

                String accessToken = "";
                if (http_code == HttpStatus.OK.value()) {
                    JSONParser parser = new JSONParser();
                    JSONObject resultObj = (JSONObject) parser.parse(okClient.getResponseBody());
                    accessToken = (String) resultObj.get("accessToken");
                    String refreshToken = (String) resultObj.get("refreshToken");
                } else {
                    //웹훅 결제조회 실패로 결제취소 처리하거나 콜백에서 처리할 수 있다.
                    System.out.println("결제조회를 위한 토큰 발급에 실패하였습니다.");
                }

                //결제 단건조회
                request = new Request().Builder()
                        .addHeader("authorization", "Bearer " + accessToken)
                        .url("https://api.portone.it/v2/payments/" + paymentId + "?storeId=" + this.storderId)
                        .build();

                okClient = new OKHttp3Client(request);
                http_code = okClient.doUsingHttp();
                System.out.println(http_code + ":" + okClient.getResponseBody());

                Portone pay_result_entity = new PortOne();

                LocalDateTime paid_at = null;
                LocalDateTime failed_at = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                if (http_code == HttpStatus.OK.value()) {
                    JSONParser parser = new JSONParser();
                    JSONObject resultObj = (JSONobject) parser.parse(okClient.getResponseBody());
                    JSONObject paymentObj = (JSONObject) resultObj.get("payment");
                    JSONObject transactions = (JSONArray) paymentObj.get("transactions");
                    for (int i = 0; i < transactions.size(); i++) {
                        JSONObject transactionObj = (JSONObject) transactions.get(i);
                        boolean is_primary = (boolean) transactionObj.get("is_primary");
                        //같은 paymentId로 결제창을 호출한 경우
                        if (is_primary == false) {
                            continue;
                        } else {
                            //최종 결제결과
                            String pay_status = (String) transactionObj.get("status");
                            pay_result_entity.setOrder_name((String) transactionObj.get("order_name"));
                            pay_result_entity.setIs_escrow((Boolean) transactionObj.get("is_escrow"));
                            JSONObject amount = (JSONObject) transactionObj.get("amount");
                            pay_result_entity.setAmount((Long) amount.get("total"));
                            pay_result_entity.setVat((Long) amount.get("vat"));
                            pay_result_entity.setCurrency((String) amount.get("currency"));
                            JSONObject customer = (JSONObject) transactionObj.get("customer");

                            if (customer != null) {
                                pay_result_entity.setBuyer_email((String) customer.get("email"));
                                pay_result_entity.setBuyer_phone((String) customer.get("phone_number"));
                                pay_result_entity.setBuyer_name((String) customer.get("name"));
                            }

                            pay_result_entity.setCustom_data((String) transactionObj.get("custom_data"));
                            pay_result_entity.setPay_method((String) transactionObj.get("method"));
                            JSONObject channel = (JSONObject) transactionObj.get("channel");
                            pay_result_entity.setPg_provider((String) channel.get("pg_provider"));

                            pay_result_entity.setStatus(pay_status);
                            if ("PAID".equals(pay_status)) {
                                pay_result_entity.setPaid_at((String) transactionObj.get("paid_at"));
                                JSONObject payment_method_detail = (JSONObject) transactionObj.get("payment_method_detail");
                                JSONObject card = (JSONObject) payment_method_detail.get("card");
                                pay_result_entity.setApproval_number((String) card.get("approval_number"));
                                JSONObject card_detail = (JSONObject) card.get("detail");
                                pay_result_entity.setCard_name((String) card_detail.get("name"));
                                JSONObject installment = (JSONObject) card.get("installment");
                                pay_result_entity.setCard_quota((Long) installment.get("month"));
                                paid_at = LocalDateTime.parse(pay_result_entity.getPaid_at().replace("T"));

                            } else if ("READY".equals(pay_status)) {
                                return "READY";
                            }
                        }
                    }
                } else {
                    //웹훅 결제조회 실패로 결제취소 처리하거나 콜백에서 처리할 수 있다.
                    System.out.println("결제 조회에 실패하였습니다.");

                }

                //custom_data에 주문테이블 pk를 실었다가 읽는다.
                String order_id = pay_result_entity.getCustom_data();

                Order order = this.orderService.findOrderById(Integer.parseInt(order_id));
                Product product = this.productService.findProductById(order.getProductId());

                //결제데이터 생성
                Payment payment = Payment.builder()
                        .userId(order.getUserId())
                        .orderId(order.getId())
                        .amount(pay_result_entity.getAmount())
                        .pgCode(pay_result_entity.getPg_provider())
                        .pgTid(pay_result_entity.getPg_tx_id)
                        .applyNum(pay_result_entity.getApproval_number())
                        .buyerEmail(pay_result_entity.getBuyer_email())
                        .cardName(pay_result_entity.getCard_name())
                        .cardQuota(pay_result_entity.getCard_quota())
                        .currency(pay_result_entity.getCurrency())
                        .impUid(txId)
                        .merchantUid(paymentId)
                        .payMethod(pay_result_entity.getPay_method())
                        .customData(pay_result_entity.getcustom_data())
                        .isEscrow(pay_result_entity.getIs_cscrow())
                        .status(pay_result_entity.getStatus())
                        .paidAt(paid_at)
                        .failedAt(failed_at)
                        .failReason(pay_result_entity.getFail_reason())
                        .build();

                this.paymentService.save(payment);

                //step5
                //주문 요청 금액과 실제 결제 금액이 같은지 비교
                if (order.getAmount().toString().equals(pay_result_entity.getAmount().toString())) {

                    if ("CANCELLED".equals(pay_result_entity.getStatus())) {
                        //주문 상태 변경
                        order.update_status("CANCEL", "결과수신시 취소로 수신");
                    } else if ("FAILED".equals(pay_result_entity.getStatus())) {
                        //주문 상태 변경
                        order.update_status("FAIL", pay_result_entity.getFail_reason());

                    } else if ("PAID".equals(pay_result_entity.getStatus())) {
                        //상품 재고 감소 및 주문 상태 변경
                        product.update_stock(product.getStock() - 1);
                        this.productService.save(product);
                        order.update_status("SUCCESS", "결제성공/가상계좌 발급 성공");
                    }
                    this.orderService.save(order);
                } else {
                    //금액 위변조 취소 처리
                    System.out.println("금액 위변조 취소처리:" + order.getAmount() + "-" + pay_result_entity.getAmount());

                    //취소 API를 전송하여 취소를 진행합니다.

                    //주문상태 변경
                    order.update.status("FAIL", pay_result_entity.getFail_reason());
                    this.orderService.save(order);
                }

                status = pay_result_entity.getStatus();

            } else {
                System.out.println("error_msg: " + message);
                status = "결제실패 : " + message;
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "결제실패 : 관리자에게 문의해주세요";
        }

        return status;
    }
}
