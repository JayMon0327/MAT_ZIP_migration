package SHOP.MAT_ZIP_migration.config.auth;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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

	private final MemberRepository memberRepository;

	/**
	 * loadUserByUsername 함수가 종료되면 Authentication 객체가 생성됨
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username);
		if(member == null) {
			return null;
		}else {
			return new PrincipalDetails(member);
		}
		
	}

}
