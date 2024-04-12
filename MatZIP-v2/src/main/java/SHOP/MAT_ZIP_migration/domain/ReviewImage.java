package SHOP.MAT_ZIP_migration.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review_image")
@Getter
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String uploadFileName; // 사용자가 업로드한 원본 파일 이름
    private String storeFileName; // 서버에 저장된 파일 이름
    private String filePath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    /**
     * 연관관계 설정 메서드
     */

    public void addReview(Review review) {
        this.review = review;
    }

    public void addFile(String uploadFileName, String storeFileName, String filePath) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.filePath = filePath;
    }
}

