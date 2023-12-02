package SHOP.MAT_ZIP_migration.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member extends AuditBaseEntity{

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

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Board> boards = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Reply> replies = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Like> likeCount = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Restaurant> restaurants = new ArrayList<>();

	/**
	 * 연관관계 편의 메서드
	 */
	public void addOrder(Order order) {
		orders.add(order);
		order.setMember(this);
	}

	public void addBoard(Board board) {
		boards.add(board);
		board.setMember(this);
	}

	public void addReply(Reply reply) {
		replies.add(reply);
		reply.setMember(this);
	}

	public void addLike(Like like) {
		likeCount.add(like);
		like.setMember(this);
	}

	public void addRestaurant(Restaurant restaurant) {
		restaurants.add(restaurant);
		restaurant.setMember(this);
	}

	/**
	 * 포인트 처리 로직
	 */
	public void addPoints(int points) {
		this.point += points; // 포인트 충전
	}

	public void subtractPoints(int points) {
		this.point -= points; // 포인트 사용
	}
}

