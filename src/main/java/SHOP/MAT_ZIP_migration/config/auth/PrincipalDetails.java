package SHOP.MAT_ZIP_migration.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import SHOP.MAT_ZIP_migration.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Secutiry Session 안에 정보를 저장할건데 -> Authentication 객체만 저장가능,
 * 이 객체안에 유저정보를 저장하려면
 * UserDetails or OAuth2User 타입이어야만 한다.
 * 하지만, 파라미터로 받을 때 관리하기가 불편하니 PrincipalDetails 타입으로 상속을 통해 묶어서 관리한다.
 */

public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	private User user;
	private Map<String, Object> attributes; // 회원정보 key,value형태를 저장

	// 일반 시큐리티 로그인시 사용
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// OAuth2.0 로그인시 사용
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	public User getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		//휴면 로그인 설정
		// 현재시간 - 로그인 시간 -> 1년을 초과하면 return false;
		return true;
	}

	//유저의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(()->{ return user.getRole().toString();});
		return collet;
	}

	// 리소스 서버로 부터 받는 회원정보
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	// User의 PrimaryKey
	@Override
	public String getName() {
		return user.getId()+"";
	}
	
}
