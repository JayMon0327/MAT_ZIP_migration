package MATZIP_ver3.controller.api;

import MATZIP_ver3.config.auth.PrincipalDetails;
import MATZIP_ver3.dto.ResponseDto;
import MATZIP_ver3.dto.order.RequestOrderDto;
import MATZIP_ver3.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "03.Order")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/order")
    @Operation(summary = "주문하기", description = "주문생성")
    public ResponseDto<Integer> order(@Valid @ModelAttribute RequestOrderDto dto,
                                      @AuthenticationPrincipal PrincipalDetails principal) {
        orderService.order(dto, principal.getMember());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/order/{orderId}")
    @Operation(summary = "주문취소", description = "주문취소")
    public ResponseDto<Integer> cancel(@PathVariable Long orderId) {
        log.info("결제 취소 요청됨" + orderId);
        orderService.cancelOrder(orderId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
