package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Payment;
import SHOP.MAT_ZIP_migration.domain.order.Address;
import SHOP.MAT_ZIP_migration.domain.order.Delivery;
import SHOP.MAT_ZIP_migration.domain.order.Item;
import SHOP.MAT_ZIP_migration.domain.order.Order;
import SHOP.MAT_ZIP_migration.domain.status.OrderStatus;
import SHOP.MAT_ZIP_migration.dto.order.ItemDto;
import SHOP.MAT_ZIP_migration.dto.order.PaymentForm;
import SHOP.MAT_ZIP_migration.dto.order.RequestOrderDto;
import SHOP.MAT_ZIP_migration.dto.order.SuccessPayment;
import SHOP.MAT_ZIP_migration.dto.order.portone.PaymentAnnotation;
import SHOP.MAT_ZIP_migration.dto.order.portone.PaymentDetail;
import SHOP.MAT_ZIP_migration.dto.order.portone.ResponsePortOne;
import SHOP.MAT_ZIP_migration.repository.ItemRepository;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import SHOP.MAT_ZIP_migration.repository.OrderRepository;
import SHOP.MAT_ZIP_migration.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class PaymentServiceTest {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private PortOneService portOneService;


    private Member member;
    private Order order;
    private Item item;
    private RequestOrderDto requestOrderDto;
    private String testImpUid = "testImpUid";

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
                .usedPoint(0)
                .totalPrice(1800) // 예상되는 최종 가격
                .storeId("testStore")
                .build();

        // 테스트용 Order 객체 생성 및 저장
        order = Order.builder()
                .member(member)
                .delivery(Delivery.builder().address(member.getAddress()).build())
                .status(OrderStatus.TRY) // 초기 상태 설정
                .build();
        orderRepository.save(order);
    }

    @DisplayName("결제 ID 테스트")
    @Test
    void paymentForm_ShouldCreatePaymentForm() {
        PaymentForm paymentForm = paymentService.paymentForm(requestOrderDto);

        assertThat(paymentForm).isNotNull();
        assertThat(paymentForm.getPaymentId()).startsWith("pid-");
        assertThat(paymentForm.getStoreId()).isEqualTo(requestOrderDto.getStoreId());
    }

    @DisplayName("결제 시 주문 상태 변경")
    @Test
    void createReservation_ShouldCreatePaymentAndChangeOrderStatus() {
        ResponsePortOne responsePortOne = ResponsePortOne.builder()
                .imp_uid(testImpUid)
                .amount(1000)
                .orderId(order.getId())
                .usedPoint(0)
                .build();
        // 테스트용 결제 상세 정보 생성 및 모의 구현
        PaymentDetail paymentDetailMock = createMockPaymentDetail(1000, testImpUid); // 결제 상세 정보의 amount를 1000으로 설정
        when(portOneService.getPaymentDetails(testImpUid)).thenReturn(paymentDetailMock);

        SuccessPayment successPayment = paymentService.createReservation(responsePortOne, member);

        Payment createdPayment = paymentRepository.findByImpUid(responsePortOne.getImp_uid());

        assertThat(successPayment).isNotNull();
        assertThat(createdPayment).isNotNull();
        assertThat(createdPayment.getOrder().getStatus()).isEqualTo(OrderStatus.ORDER);
    }

    @DisplayName("결제 금액 검증")
    @Test
    void createReservation_ShouldVerifyPriceCorrectly() {
        // 테스트용 결제 상세 정보 생성
        PaymentDetail paymentDetailMock = createMockPaymentDetail(1000,testImpUid); // 결제 상세 정보의 amount를 1000으로 설정
        int expectedAmount = 1000;

        // PortOneService의 getPaymentDetails 메서드를 모의 구현
        when(portOneService.getPaymentDetails(testImpUid)).thenReturn(paymentDetailMock);

        // 테스트용 결제 응답 생성
        ResponsePortOne responsePortOne = ResponsePortOne.builder()
                .imp_uid(testImpUid)
                .amount(expectedAmount)
                .orderId(order.getId())
                .usedPoint(0)
                .build();

        // createReservation 메서드 실행
        SuccessPayment successPayment = paymentService.createReservation(responsePortOne, member);

        // 결과 검증
        assertThat(successPayment).isNotNull();
        // createReservation 메서드 내부에서 verifyPrice 메서드가 호출되며, 올바른 금액이 검증되는지 확인
        assertThat(successPayment.getAmount()).isEqualTo(1000);
    }

    @DisplayName("회원 추가 적립금 테스트")
    @Test
    void memberAddPoint() {
        Integer resultPoint = member.calculatePoint(0, 3000);
        assertThat(resultPoint).isEqualTo(150);
    }

    @DisplayName("포인트 결제 금액 검증")
    @Test
    void createReservation_ShouldVerifyPointCorrectly() {
        Member newMember = createNewMember();
        Integer initialPoint = newMember.getPoint();
        // 테스트용 결제 상세 정보 생성
        PaymentDetail paymentDetailMock = createMockPaymentDetail(1000,testImpUid); // 결제 상세 정보의 amount를 1000으로 설정
        int expectedAmount = 1000;

        // PortOneService의 getPaymentDetails 메서드를 모의 구현
        when(portOneService.getPaymentDetails(testImpUid)).thenReturn(paymentDetailMock);

        // 테스트용 결제 응답 생성
        ResponsePortOne responsePortOne = ResponsePortOne.builder()
                .imp_uid(testImpUid)
                .amount(expectedAmount)
                .orderId(order.getId())
                .usedPoint(300)
                .build();

        // createReservation 메서드 실행
        paymentService.createReservation(responsePortOne, newMember);
        int realPricePayment = initialPoint - responsePortOne.getUsedPoint();
        double earnedPoints = (initialPoint - responsePortOne.getUsedPoint()) * 0.05; // 5% 적립
        int earnedPointsInteger = ((int) Math.round(earnedPoints));

        assertThat(newMember.getPoint()).isEqualTo(realPricePayment + earnedPointsInteger);
    }

    private PaymentDetail createMockPaymentDetail(int amount, String impUid) {
        PaymentDetail paymentDetail = new PaymentDetail();
        PaymentAnnotation response = new PaymentAnnotation();
        response.setAmount(amount);
        paymentDetail.setResponse(response);

        // 테스트용 Order 객체 생성
        Order testOrder = Order.builder()
                .member(member)
                .delivery(Delivery.builder().address(member.getAddress()).build())
                .status(OrderStatus.ORDER)
                .build();
        orderRepository.save(testOrder);

        // 테스트용 Payment 객체 생성 및 저장
        Payment payment = Payment.builder()
                .impUid(impUid)
                .order(testOrder) // 위에서 생성한 Order 객체 설정
                .build();
        paymentRepository.save(payment);

        return paymentDetail;
    }

    private Member createNewMember() {
        Member newMember = Member.builder()
                .username("testUser")
                .password("password")
                .email("test@example.com")
                .address(new Address("TestCity", "TestStreet", "12345"))
                .point(1000)
                .build();
        return memberRepository.save(newMember);
    }
}