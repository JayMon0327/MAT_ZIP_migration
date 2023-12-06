package SHOP.MAT_ZIP_migration.domain.order;

import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.handler.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Table(name = "item")
@Builder
public class Item {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

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
     * @param quantity
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     * @param quantity
     */
    public void removeStock(int quantity) {
        int result = this.stockQuantity - quantity;
        if (result < 0) {
            throw new NotEnoughStockException("재고가 없습니다.");
        }
        this.stockQuantity = result;
    }

    /**
     * 상품 수정
     */
    public void change(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

}
