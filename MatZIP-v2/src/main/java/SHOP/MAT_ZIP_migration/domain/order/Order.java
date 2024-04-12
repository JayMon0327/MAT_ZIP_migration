package SHOP.MAT_ZIP_migration.domain.order;

import SHOP.MAT_ZIP_migration.domain.baseentity.CreateDateBaseEntity;
import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.status.OrderStatus;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
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
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 주문 로직
     */
    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .orderItems(orderItems)
                .status(OrderStatus.TRY)
                .build();
        return order;
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
