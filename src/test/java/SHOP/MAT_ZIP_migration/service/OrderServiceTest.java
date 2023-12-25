package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.order.Address;
import SHOP.MAT_ZIP_migration.domain.order.Delivery;
import SHOP.MAT_ZIP_migration.domain.order.Item;
import SHOP.MAT_ZIP_migration.domain.order.Order;
import SHOP.MAT_ZIP_migration.domain.status.OrderStatus;
import SHOP.MAT_ZIP_migration.dto.order.*;
import SHOP.MAT_ZIP_migration.repository.ItemRepository;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import SHOP.MAT_ZIP_migration.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private Item item;
    private OrderForm orderForm;
    private RequestOrderDto requestOrderDto;

    @BeforeEach
    void setUp() {
        // 테스트용 회원 객체 생성 및 설정
        member = Member.builder()
                .username("testUser")
                .password("password")
                .email("test@example.com")
                .address(new Address("TestCity", "TestStreet", "12345"))
                .point(1000)
                .build();
        memberRepository.save(member);

        // 테스트용 상품 객체 생성 및 설정
        item = Item.builder()
                .name("TestItem")
                .price(1000)
                .stock(100)
                .build();
        itemRepository.save(item); // 상품 저장

        // 테스트용 주문 DTO 생성 및 설정
        List<ItemDto> itemDtos = new ArrayList<>();
        itemDtos.add(new ItemDto(item.getId(), item.getName(), 2));
        requestOrderDto = RequestOrderDto.builder()
                .itemDtos(itemDtos)
                .address(new Address("TestCity", "TestStreet", "12345"))
                .usedPoint(200)
                .totalPrice(1800) // 예상되는 최종 가격
                .storeId("testStore")
                .build();
    }

    @DisplayName("주문 생성 및 검증")
    @Test
    void testOrderCreation() {
        PaymentForm result = orderService.order(requestOrderDto, member);
        assertThat(result).isNotNull();
        assertThat(result.getOrder()).isNotNull();
        assertThat(result.getOrderItems()).hasSize(1);
        assertThat(result.getFinalPrice()).isEqualTo(1800);
    }

    @DisplayName("주문 취소 및 검증")
    @Test
    void testOrderCancellation() {
        PaymentForm paymentForm = orderService.order(requestOrderDto, member);
        Order createdOrder = orderRepository.findById(paymentForm.getOrder().getId()).orElseThrow();
        orderService.cancelOrder(createdOrder.getId());
        Order cancelledOrder = orderRepository.findById(createdOrder.getId()).orElseThrow();
        assertThat(cancelledOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @DisplayName("금액 계산 테스트")
    @Test
    void totalPriceCalculationTest() {
        int expectedTotalPrice = item.getPrice() * requestOrderDto.getItemDtos().get(0).getCount() - requestOrderDto.getUsedPoint();
        PaymentForm paymentForm = orderService.order(requestOrderDto, member);

        assertThat(paymentForm.getFinalPrice()).isEqualTo(expectedTotalPrice);
    }
}
