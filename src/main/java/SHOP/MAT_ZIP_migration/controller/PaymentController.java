package SHOP.MAT_ZIP_migration.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    @GetMapping("/payment/successPage")
    public String successPayment() {
        return "order/successPayment";
    }
}
