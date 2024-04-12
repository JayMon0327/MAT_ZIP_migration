package MATZIP_ver3.domain;

import MATZIP_ver3.domain.baseentity.DateBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "review")
public class Review extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;
    private Integer rating;

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */

    public void addImage(ReviewImage reviewImage) {
        this.reviewImages.add(reviewImage);
        reviewImage.addReview(this);
    }
}
