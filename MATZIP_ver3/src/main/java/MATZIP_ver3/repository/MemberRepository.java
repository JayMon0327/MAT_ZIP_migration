package MATZIP_ver3.repository;

import java.util.Optional;

import MATZIP_ver3.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // SELECT * FROM user WHERE username = ?1
    Member findByUsername(String username);

    // SELECT * FROM user WHERE provider = ?1 and providerId = ?2
    Optional<Member> findByProviderAndProviderId(String provider, String providerId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);
}


