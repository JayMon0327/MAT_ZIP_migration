package SHOP.MAT_ZIP_migration.controller;

import SHOP.MAT_ZIP_migration.dto.order.PaymentList;
import SHOP.MAT_ZIP_migration.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment/successPage")
    public String successPayment() {
        return "order/successPayment";
    }

    @GetMapping("/payment/detail/{memberId}")
    public String viewPayments(@PathVariable Long memberId,
                              @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {
        Page<PaymentList> paymentDetails = paymentService.getPaymentDetails(memberId,pageable);
        model.addAttribute("payments", paymentDetails);
        log.info("결제내역" + paymentDetails.getContent());
        return "order/list";
    }
}
