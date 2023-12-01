package SHOP.MAT_ZIP_migration.repository;

import java.util.Optional;

import SHOP.MAT_ZIP_migration.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long>{
	// SELECT * FROM user WHERE username = ?1
	Member findByUsername(String username);
	
	// SELECT * FROM user WHERE provider = ?1 and providerId = ?2
	Optional<Member> findByProviderAndProviderId(String provider, String providerId);

}


