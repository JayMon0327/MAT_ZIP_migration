package SHOP.MAT_ZIP_migration.domain.order;

import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.handler.NotEnoughStockException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private Integer price;
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    /**
     * 사용자 정의 생성자
     */
    public void addProduct(Product product) {
        this.product = product;
    }

    /**
     * stock 증가(재고증가)
     *
     * @param quantity
     */
    public void addStock(Integer quantity) {
        this.stock += quantity;
    }

    /**
     * stock 감소
     *
     * @param quantity
     */
    public void removeStock(Integer quantity) {
        int result = this.stock - quantity;
        if (result < 0) {
            throw new NotEnoughStockException("재고가 없습니다.");
        }
        this.stock = result;
    }

    /**
     * 상품 수정
     */
    public void change(String name, Integer price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

}

