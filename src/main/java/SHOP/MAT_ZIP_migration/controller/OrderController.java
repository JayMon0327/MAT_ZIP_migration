package SHOP.MAT_ZIP_migration.controller;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.dto.order.OrderForm;
import SHOP.MAT_ZIP_migration.dto.order.ResponseOrderForm;
import SHOP.MAT_ZIP_migration.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public String orderForm(@Valid @ModelAttribute OrderForm form, @AuthenticationPrincipal PrincipalDetails principal,
                            Model model) {
        ResponseOrderForm orderForm = orderService.orderForm(form, principal.getMember());
        model.addAttribute("order", orderForm);
        return "order/orderForm";
    }
}
