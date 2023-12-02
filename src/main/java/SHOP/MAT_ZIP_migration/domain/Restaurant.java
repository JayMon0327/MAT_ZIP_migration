package SHOP.MAT_ZIP_migration.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String storeName;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menus = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */
    public void addMenu(Menu menu) {
        menus.add(menu);
        menu.setRestaurant(this);
    }
}
