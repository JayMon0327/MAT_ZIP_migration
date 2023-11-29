package SHOP.MAT_ZIP_migration.config.auth;

import SHOP.MAT_ZIP_migration.model.User;
import SHOP.MAT_ZIP_migration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 시큐리티 loginProcessingUrl 요청이 오면
 * 자동으로 loadUserByUsername 함수 실행
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;

	/**
	 * loadUserByUsername 함수가 종료되면 Authentication 객체가 생성됨
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return null;
		}else {
			return new PrincipalDetails(user);
		}
		
	}

}
