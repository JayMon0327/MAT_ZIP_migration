package SHOP.MAT_ZIP_migration.controller;

import SHOP.MAT_ZIP_migration.dto.order.OrderForm;
import SHOP.MAT_ZIP_migration.dto.product.RequestSaveItemDto;
import SHOP.MAT_ZIP_migration.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public String orderForm(@Valid @ModelAttribute OrderForm form, Model model) {
        model.addAttribute("order", form);
        log.info("orderModel객체"+ form);
        return "order/orderForm";
    }
}
