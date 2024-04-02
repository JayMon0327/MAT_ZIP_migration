package MATZIP_ver3.controller.api;

import MATZIP_ver3.config.auth.PrincipalDetails;
import MATZIP_ver3.dto.order.SuccessPayment;
import MATZIP_ver3.dto.order.portone.ResponsePortOne;
import MATZIP_ver3.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "02.Payment")
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping("/payment/order")
    @Operation(summary = "결제하기" , description = "결제생성")
    public ResponseEntity<SuccessPayment> createOrder(@Valid @RequestBody ResponsePortOne dto,
                                                      @AuthenticationPrincipal PrincipalDetails principal) {
        SuccessPayment res = paymentService.createReservation(dto, principal.getMember());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
