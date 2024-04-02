package MATZIP_ver3.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentList {
    private String impUid;
    private Long orderId;
    private Integer amount;
    private LocalDateTime createDate;
}
