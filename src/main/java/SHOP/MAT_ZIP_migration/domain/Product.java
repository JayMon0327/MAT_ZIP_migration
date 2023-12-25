package SHOP.MAT_ZIP_migration.domain;

import SHOP.MAT_ZIP_migration.domain.baseentity.DateBaseEntity;
import SHOP.MAT_ZIP_migration.domain.order.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "product")
public class Product extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private List<Item> items = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    /**
     * 사용자 정의 생성자
     */

    public void updateProduct(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * 연관관계 편의 메서드
     */
    public void addImage(ProductImage productImage) {
        this.productImages.add(productImage);
        productImage.addProduct(this);
    }

    public void clearImages() {
        this.productImages.clear();
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.addProduct(this);
    }
}
