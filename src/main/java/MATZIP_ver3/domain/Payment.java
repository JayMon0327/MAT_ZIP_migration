package MATZIP_ver3.domain;

import MATZIP_ver3.domain.baseentity.DateBaseEntity;
import MATZIP_ver3.domain.order.Order;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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

    private String impUid;
    private String merchantUid;

    public void saveCancelAmount(Integer cancelAmount) {
        this.cancelAmount = cancelAmount;
    }

    public void getDateEntity() {
    }

}
