package SHOP.MAT_ZIP_migration.controller;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.domain.order.Item;
import SHOP.MAT_ZIP_migration.domain.order.Order;
import SHOP.MAT_ZIP_migration.dto.order.OrderForm;
import SHOP.MAT_ZIP_migration.dto.order.PaymentForm;
import SHOP.MAT_ZIP_migration.dto.order.RequestOrderDto;
import SHOP.MAT_ZIP_migration.dto.order.ResponseOrderForm;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import SHOP.MAT_ZIP_migration.repository.ItemRepository;
import SHOP.MAT_ZIP_migration.service.OrderService;
import SHOP.MAT_ZIP_migration.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping("/order")
    public String orderForm(@Valid @ModelAttribute OrderForm form, @AuthenticationPrincipal PrincipalDetails principal,
                            Model model) {
        ResponseOrderForm orderForm = orderService.orderForm(form, principal.getMember());
        model.addAttribute("order", orderForm);
        return "order/orderForm";
    }

    @GetMapping("/order/paymentForm")
    public String order(@Valid @ModelAttribute RequestOrderDto dto,
                        @AuthenticationPrincipal PrincipalDetails principal, Model model) {
        //주문 페이지 접속시 주문을 생성하고, 상품의 재고 유무를 확인하여 결제 페이지로 이동시킴
        orderService.order(dto,principal.getMember());
        PaymentForm form = paymentService.requestPaymentForm(dto);

        //포트원 sdk 채널키와 결제 데이터 정보
        model.addAttribute("paymentForm", form);
        // 회원, 상품, 주문 정보
        model.addAttribute("member", principal.getMember());
        model.addAttribute("product", form.getOrderItems());
        model.addAttribute("order", form.getOrder());

        return "checkout_v2"; //결제 페이지로 이동
    }


}
