package MATZIP_ver3.dto.order.portone;

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
