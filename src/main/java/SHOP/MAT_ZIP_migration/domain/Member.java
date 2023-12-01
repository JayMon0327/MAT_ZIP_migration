package SHOP.MAT_ZIP_migration.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

// ORM - Object Relation Mapping

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class Member {

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

	@CreatedDate
	private LocalDateTime createDate;

	@LastModifiedDate
	private Timestamp updateDate;

	@OneToMany(mappedBy = "member")
	private List<Order> orders;

	@OneToMany(mappedBy = "member")
	private List<Board> boards;

	@OneToMany(mappedBy = "member")
	private List<Reply> replies;

	@OneToMany(mappedBy = "member")
	private List<Like> likeCount;

	@OneToMany(mappedBy = "member")
	private List<Restaurant> restaurants;
}

