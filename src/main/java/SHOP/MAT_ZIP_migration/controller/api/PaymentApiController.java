package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.dto.order.ResPayment;
import SHOP.MAT_ZIP_migration.dto.order.ResponsePortOne;
import SHOP.MAT_ZIP_migration.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping("/payment/callback")
    public ResponseEntity<JSONObject> callback_receive(@RequestBody ResponsePortOne dto,
                                                       @AuthenticationPrincipal PrincipalDetails principal) {
        ResPayment reservation = paymentService.createReservation(dto, principal.getMember());
        log.info("payment콜백 호출됨" + reservation);
        return new ResponseEntity<>(reservation.getResponse(), reservation.getHeaders(), HttpStatus.OK);
    }

    @PostMapping("/payment/complete")
    public ResponseDto<Integer> completePayment() {
        log.info("컴프리트 호출댐");
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
