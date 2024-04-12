package MATZIP_ver3.controller;

import MATZIP_ver3.config.auth.PrincipalDetails;
import MATZIP_ver3.dto.order.OrderForm;
import MATZIP_ver3.dto.order.PaymentForm;
import MATZIP_ver3.dto.order.RequestOrderDto;
import MATZIP_ver3.dto.order.ResponseOrderForm;
import MATZIP_ver3.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/order/paymentForm")
    public String order(@Valid @ModelAttribute RequestOrderDto dto,
                        @AuthenticationPrincipal PrincipalDetails principal, Model model) {
        //주문 페이지 접속시 주문을 생성하고, 상품의 재고 유무를 확인하여 결제 페이지로 이동시킴
        PaymentForm form = orderService.order(dto, principal.getMember());
        model.addAttribute("paymentForm", form);
        return "order/paymentForm"; //결제 페이지로 이동
    }
}
