package SHOP.MAT_ZIP_migration.domain;

import java.util.ArrayList;
import java.util.List;

import SHOP.MAT_ZIP_migration.domain.baseentity.DateBaseEntity;
import SHOP.MAT_ZIP_migration.domain.order.Order;
import SHOP.MAT_ZIP_migration.domain.status.Role;
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

	private String email;

	@Enumerated(EnumType.STRING)
	private Role role; //ROLE_USER, ROLE_ADMIN

	private String provider;
	private String providerId;

	@Column(columnDefinition = "integer default 0", nullable = false)
	private int point;

	@Builder.Default
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "member")
	private List<Product> products = new ArrayList<>();




	/**
	 * 연관관계 편의 메서드
	 */
//	public void addOrder(Order order) {
//		orders.add(order);
//		order.setMember(this);
//	}

	/**
	 * 포인트 처리 로직
	 */
	public void addPoints(int points) {
		this.point += points; // 포인트 충전
	}

	public void subtractPoints(int points) {
		this.point -= points; // 포인트 사용
	}

	/**
	 * 회원 수정
	 */
	public void updateMember(String password, String email) {
		this.password = password;
		this.email = email;
	}

	/**
	 * OAuth2 로그인 유저 이메일 등록
	 */
	public void updateEmail(String email) {
		this.email = email;
	}
}

