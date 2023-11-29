package SHOP.MAT_ZIP_migration.model;

import java.sql.Timestamp;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM - Object Relation Mapping

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	private String password;

	private String email;

	@Enumerated(EnumType.STRING)
	private Role role; //ROLE_USER, ROLE_ADMIN

	// OAuth를 위해 구성한 추가 필드 2개
	private String provider;
	private String providerId;

	@CreationTimestamp
	private Timestamp createDate;
}
