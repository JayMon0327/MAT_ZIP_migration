package SHOP.MAT_ZIP_migration.repository;

import java.util.Optional;

import SHOP.MAT_ZIP_migration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
	// SELECT * FROM user WHERE username = ?1
	User findByUsername(String username);
	
	// SELECT * FROM user WHERE provider = ?1 and providerId = ?2
	Optional<User> findByProviderAndProviderId(String provider, String providerId);

}


