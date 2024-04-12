package SHOP.MAT_ZIP_migration.dto.order.portone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthAnnotation {
    private String access_token;
    private Integer expired_at;
    private Integer now;
}
