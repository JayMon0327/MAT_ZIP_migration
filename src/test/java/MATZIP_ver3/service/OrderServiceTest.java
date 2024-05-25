package MATZIP_ver3.service;

import MATZIP_ver3.domain.Member;
import MATZIP_ver3.domain.order.Address;
import MATZIP_ver3.domain.order.Item;
import MATZIP_ver3.domain.order.Order;
import MATZIP_ver3.domain.status.OrderStatus;
import MATZIP_ver3.dto.order.*;
import MATZIP_ver3.repository.ItemRepository;
import MATZIP_ver3.repository.MemberRepository;
import MATZIP_ver3.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
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

    @DisplayName("금액 계산 테스트")
    @Test
    void totalPriceCalculationTest() {
        int expectedTotalPrice = item.getPrice() * requestOrderDto.getItemDtos().get(0).getCount() - requestOrderDto.getUsedPoint();
        PaymentForm paymentForm = orderService.order(requestOrderDto, member);

        assertThat(paymentForm.getFinalPrice()).isEqualTo(expectedTotalPrice);
    }
}
