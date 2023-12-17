package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.order.*;
import SHOP.MAT_ZIP_migration.domain.status.OrderStatus;
import SHOP.MAT_ZIP_migration.dto.order.*;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import SHOP.MAT_ZIP_migration.repository.ItemRepository;
import SHOP.MAT_ZIP_migration.repository.OrderRepository;
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
     * 재고 확인 예외처리 필요
     * 결제 정보 콜백 시, OrderStatus 변경 메소드 필요
     */

    @Transactional
    public PaymentForm order(RequestOrderDto dto, Member member) {
        List<OrderItem> orderItems = createOrderItem(dto);
        Delivery delivery = Delivery.createDelivery(dto.getAddress());
        Order order = Order.createOrder(member, delivery, orderItems);

        PaymentForm paymentForm = paymentService.paymentForm(dto);
        paymentForm.setOrder(order);
        paymentForm.setOrderItems(orderItems);
        paymentForm.setFinalPrice(checkFinalPrice(dto));

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
            item.removeStock(itemDto.getCount());
        }
        return orderItems;
    }

    /**
     * 최종 결제 금액, 포인트
     */

    private int checkFinalPrice(RequestOrderDto dto) {
        List<ItemDto> itemDtos = dto.getItemDtos();
        int totalPrice = calculateTotalPrice(itemDtos);
        int finalPrice = totalPrice - dto.getUsedPoint();
        return finalPrice;
    }

    /**
     * 주문취소 - 포인트 원복 관련 로직 필요, 배송 취소 처리 필요
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new CustomException(CustomErrorCode.NOT_FOUND_ORDER));
        order.cancel();
    }






    public Order updateOrderStatus(Long orderId, String paymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_ORDER));

        // 결제 상태에 따라 주문 상태 업데이트
        if ("paid".equals(paymentStatus)) {
            order.changeOrderStatus(OrderStatus.ORDER);
        } else if ("failed".equals(paymentStatus)) {
            order.changeOrderStatus(OrderStatus.FAIL);
        }

        orderRepository.save(order);

        return order;
    }

}
