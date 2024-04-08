package MATZIP_ver3.domain.order;

import MATZIP_ver3.domain.baseentity.CreateDateBaseEntity;
import MATZIP_ver3.domain.Member;
import MATZIP_ver3.domain.status.OrderStatus;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order extends CreateDateBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 주문 로직
     */
    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setStatus(OrderStatus.TRY);

        // 각 OrderItem에 대해 Order 설정
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


    public void changeOrderStatus(OrderStatus status) {
        this.status = status;
    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new CustomException(CustomErrorCode.ALREADY_DELIVERY_STATUS);
        }
        if (status == OrderStatus.TRY || status == OrderStatus.FAIL) {
            throw new CustomException(CustomErrorCode.NOT_NORMAL_PAYMENT_ORDER);
        }
        this.status = (OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

}
