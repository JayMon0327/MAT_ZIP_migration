package MATZIP_ver3.dto.order.portone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetail {
    private String code;
    private String message;
    private PaymentAnnotation response;
}
