package MATZIP_ver3.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessPayment {
    private String orderName; // 제품명
    private int amount; // 총 결제 금액
    private String buyerName; // 구매자
}
