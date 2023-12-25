package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.dto.order.RequestOrderDto;
import SHOP.MAT_ZIP_migration.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseDto<Integer> order(@Valid @ModelAttribute RequestOrderDto dto,
                                      @AuthenticationPrincipal PrincipalDetails principal) {
        orderService.order(dto, principal.getMember());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseDto<Integer> cancel(@PathVariable Long orderId) {
        log.info("결제 취소 요청됨" + orderId);
        orderService.cancelOrder(orderId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
