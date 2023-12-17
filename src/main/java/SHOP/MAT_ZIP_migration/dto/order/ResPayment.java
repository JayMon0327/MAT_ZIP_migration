package SHOP.MAT_ZIP_migration.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;

@Data
@AllArgsConstructor
public class ResPayment {
    private JSONObject response;
    private HttpHeaders headers;
}
