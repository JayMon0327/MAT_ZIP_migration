package SHOP.MAT_ZIP_migration.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String fileName; // 파일 이름
    private String filePath; // 파일 경로 또는 URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    /**
     * 연관관계 설정 메서드
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setReview(Review review) {
        this.review = review;
    }

}

