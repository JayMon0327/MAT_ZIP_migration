package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Payment;
import SHOP.MAT_ZIP_migration.domain.order.*;
import SHOP.MAT_ZIP_migration.dto.order.*;
import SHOP.MAT_ZIP_migration.dto.order.portone.PaymentDetail;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import SHOP.MAT_ZIP_migration.repository.ItemRepository;
import SHOP.MAT_ZIP_migration.repository.OrderRepository;
import SHOP.MAT_ZIP_migration.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final PortOneService portOneService;

    /**
     * orderForm = 상품 이름, 구매 수량, 총 구매금액, 판매자 이름, 배송지 정보
     */
    public ResponseOrderForm orderForm(OrderForm form, Member member) {
        ResponseOrderForm orderForm = ResponseOrderForm.builder()
                .items(itemDtoTransfer(form.getItems()))
                .address(member.getAddress())
                .storeId(form.getStoreId())
                .totalPrice(calculateTotalPrice(form.getItems()))
                .build();
        return orderForm;
    }

    private List<ItemDto> itemDtoTransfer(List<ItemDto> dtos) {
        List<ItemDto> itemDtos = dtos.stream()
                .map(item -> new ItemDto(item.getItemId(),item.getItemName(), item.getCount()))
                .collect(Collectors.toList());
        return itemDtos;
    }

    private int calculateTotalPrice(List<ItemDto> itemDtos) {
        int totalPrice = 0;
        for (ItemDto itemDto : itemDtos) {
            Item item = itemRepository.findById(itemDto.getItemId())
                    .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_ITEM));

            int itemTotalPrice = item.getPrice() * itemDto.getCount();
            totalPrice += itemTotalPrice;
        }
        return totalPrice;
    }

    /**
     * 주문생성 API
     * 재고 검증 후 주문 생성
     * 결제 승인 전이므로 주문 상태를 TRY 설정
     * 사용된 포인트를 제외한 금액을 결제요청에 넘김
     */

    @Transactional
    public PaymentForm order(RequestOrderDto dto, Member member) {
        List<OrderItem> orderItems = createOrderItem(dto);
        Delivery delivery = Delivery.createDelivery(dto.getAddress());
        Order order = Order.createOrder(member, delivery, orderItems);

        PaymentForm paymentForm = paymentService.paymentForm(dto);
        paymentForm.setOrder(order);
        paymentForm.setOrderItems(orderItems);
        paymentForm.setFinalPrice(checkPrice(dto));

        orderRepository.save(order);
        return paymentForm;
    }

    private List<Integer> orderPrice(List<ItemDto> dtos) {
        List<Integer> orderPrices = new ArrayList<>();
        for (ItemDto dto : dtos) {
            Item item = itemRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_ITEM));
            int orderPrice = item.getPrice() * dto.getCount();
            orderPrices.add(orderPrice);
        }
        return orderPrices;
    }

    private List<OrderItem> createOrderItem(RequestOrderDto dto) {
        List<OrderItem> orderItems = new ArrayList<>();
        List<Integer> prices = orderPrice(dto.getItemDtos());

        for (int i = 0; i < dto.getItemDtos().size(); i++) {
            ItemDto itemDto = dto.getItemDtos().get(i);
            Item item = itemRepository.findById(itemDto.getItemId())
                    .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_ITEM));

            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .orderPrice(prices.get(i))
                    .count(itemDto.getCount())
                    .build();
            orderItems.add(orderItem);
            //재고 처리와 검증
            item.removeStock(itemDto.getCount());
        }
        return orderItems;
    }

    private int checkPrice(RequestOrderDto dto) {
        List<ItemDto> itemDtos = dto.getItemDtos();
        int totalPrice = calculateTotalPrice(itemDtos);
        int finalPrice = totalPrice - dto.getUsedPoint();
        return finalPrice;
    }

    /**
     * 주문취소
     * 1. 결제 조회 API로 주문 정보 검증
     * 2. 재고 원복, 주문 상태 변경
     * 3. 포인트 원복 관련 로직 필요
     * 4. 결제내역 테이블에 취소 금액 저장
     * 5. 결제 취소 API로 주문 취소 처리
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //결제 조회 API로 주문 정보 검증
        Payment payment = paymentRepository.findByOrderId(orderId);
        paymentService.verifyPrice(payment.getImpUid(), payment.getAmount());

        //재고 원복, 주문 상태 변경
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new CustomException(CustomErrorCode.NOT_FOUND_ORDER));
        order.cancel();

        //포인트 원복 처리
        Member member = order.getMember();
        member.cancelOrderPoint(payment.getUsedPoint(), payment.getAddPoint());

        //결제내역 테이블에 취소 금액 저장 - 현재 부분 취소 처리 기능 없음
        payment.saveCancelAmount(payment.getAmount());

        //결제 취소 API로 주문 취소 처리
        portOneService.cancelPayment(payment.getImpUid(), payment.getMerchantUid(),payment.getAmount());
    }
}
