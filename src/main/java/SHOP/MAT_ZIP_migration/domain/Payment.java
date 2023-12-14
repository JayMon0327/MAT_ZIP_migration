package SHOP.MAT_ZIP_migration.domain;

import SHOP.MAT_ZIP_migration.domain.baseentity.DateBaseEntity;
import SHOP.MAT_ZIP_migration.domain.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "payment")
public class Payment extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer amount;
    private Integer cancelAmount;

    private Integer addPoint;
    private Integer usedPoint;

    private String pg_code;
}
