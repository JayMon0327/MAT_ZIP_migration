package MATZIP_ver3.domain;

import java.util.ArrayList;
import java.util.List;

import MATZIP_ver3.domain.baseentity.DateBaseEntity;
import MATZIP_ver3.domain.order.Address;
import MATZIP_ver3.domain.order.Order;
import MATZIP_ver3.domain.status.Role;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "member")
public class Member extends DateBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String password;

    private String nickName;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; //ROLE_USER, ROLE_ADMIN

    private String provider;
    private String providerId;

    private Integer point;
    private Address address;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Product> products = new ArrayList<>();


    /**
     * 포인트 처리 로직
     */
    private void addPoints(int points) {
        this.point += points; // 포인트 충전
    }

    private void removePoints(int points) {
        int result = this.point - points;
        if (result < 0) {
            throw new CustomException(CustomErrorCode.NOT_ENOUGH_POINT);
        }
        this.point -= points; // 포인트 사용
    }

    public Integer calculatePoint(Integer usedPoint, Integer amount) {
        this.removePoints(usedPoint);

        double earnedPoints = (amount - usedPoint) * 0.05; // 5% 적립
        int earnedPointsInteger = ((int) Math.round(earnedPoints));
        this.addPoints(earnedPointsInteger);
        return earnedPointsInteger;
    }

    public void cancelOrderPoint(Integer usedPoint, Integer addPoint) {
        this.addPoints(usedPoint);
        this.removePoints(addPoint);
    }

    /**
     * 회원 수정
     */
    public void updateMember(String nickName, String email, Address address) {
        this.nickName = nickName;
        this.email = email;
        this.address = address;
    }

    /**
     * 비밀번호 변경
     */
    public void updatePassword(String password) {
        this.password = password;
    }

    /**
     * OAuth2 로그인 유저 이메일 등록
     */
    public void updateEmail(String email) {
        this.email = email;
    }
}

