package SHOP.MAT_ZIP_migration.dto.order.portone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
    private Integer code;
    private String message;
    private AuthAnnotation response;
}
