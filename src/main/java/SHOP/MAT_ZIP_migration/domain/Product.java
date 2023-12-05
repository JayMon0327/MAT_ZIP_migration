package SHOP.MAT_ZIP_migration.domain;

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
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String description;
    private int price;
    private int stock;

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();

    /**
     * 사용자 정의 생성자
     */

    public void updateProduct(String title, String description, int price, int stock) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    /**
     * 연관관계 편의 메서드
     */
    public void addImage(Image image) {
        this.images.add(image);
        image.setProduct(this);
    }

    public void clearImages() {
        this.images.clear();
    }
}
