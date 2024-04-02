package MATZIP_ver3.dto.order.portone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAnnotation {
    private String imp_uid;
    private String merchant_uid;
    private int amount;
    private String buyer_name;
    private String name; //제품명
    private String pg_provider;
}
