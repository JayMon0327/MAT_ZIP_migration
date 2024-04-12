package MATZIP_ver3.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer orderPrice;
    private Integer count;

    public void cancel() {
        getItem().addStock(count);
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
