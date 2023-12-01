package SHOP.MAT_ZIP_migration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @Column(columnDefinition = "integer default 0", nullable = false) //조회수 디폴트값 0, int타입이므로 null방지
    private int viewCounts; //조회수

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"board"}) //무한 참조 방지
    @OrderBy("id desc")
    private List<Reply> replys = new ArrayList<>();


    @OneToMany(mappedBy = "board")
    private List<Like> likeCount = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */
    public void addReply(Reply reply) {
        replys.add(reply);
        reply.setBoard(this);
    }

    public void addLike(Like like) {
        likeCount.add(like);
        like.setBoard(this);
    }

}



