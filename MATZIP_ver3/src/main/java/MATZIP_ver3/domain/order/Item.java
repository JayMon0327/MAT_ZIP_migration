package MATZIP_ver3.domain.order;

import MATZIP_ver3.domain.Product;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
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
     */
    public void addStock(Integer count) {
        this.stock += count;
    }

    /**
     * stock 감소
     */
    public void removeStock(Integer count) {
        int result = this.stock - count;
        if (result < 0) {
            throw new CustomException(CustomErrorCode.NOT_ENOUGH_STOCK);
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

