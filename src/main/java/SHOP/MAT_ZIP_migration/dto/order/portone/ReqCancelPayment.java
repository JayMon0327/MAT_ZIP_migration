package SHOP.MAT_ZIP_migration.dto.order.portone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqCancelPayment {
    //필수
    private String imp_uid;
    private String merchant_uid;
    private Integer checksum;

    //선택
    private Integer tax_free; // (부분) 취소요청 금액 중 면세 금액
    private Integer vat_amount; //(부분) 취소요청 금액 중 부가세 금액
    private String reason; //취소 사유
    private String refund_holder; //환불계좌 예금주
    private String refund_bank; //환금계좌 은행코드
    private String refund_account; // 환불계좌 계좌번호
    private String refund_tel; //환불계좌 예금주 연락처
}
