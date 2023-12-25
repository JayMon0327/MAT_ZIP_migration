package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.dto.order.SuccessPayment;
import SHOP.MAT_ZIP_migration.dto.order.portone.ResponsePortOne;
import SHOP.MAT_ZIP_migration.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping("/payment/order")
    public ResponseEntity<SuccessPayment> createOrder(@Valid @RequestBody ResponsePortOne dto,
                                      @AuthenticationPrincipal PrincipalDetails principal) {
        SuccessPayment res = paymentService.createReservation(dto, principal.getMember());
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
