package SHOP.MAT_ZIP_migration.config;

import SHOP.MAT_ZIP_migration.config.oauth.PrincipalOauth2UserService;
import SHOP.MAT_ZIP_migration.domain.Role;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * OAuth 로그인
 * 스프링 부트 3.0 , 시큐리티 버전 6.0으로 버전업에 따른 문법 수정
 */

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

	private final PrincipalOauth2UserService principalOauth2UserService;

	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf((csrfConfig) ->
						csrfConfig.disable()
				)
				.authorizeHttpRequests(auth -> auth
						.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/user/**")).hasRole(Role.USER.name())
						.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole(Role.ADMIN.name())
						.anyRequest().permitAll()
				)

				.formLogin(form -> form
						.loginPage("/loginForm")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/")
				)
				.oauth2Login((oauth) -> oauth
						.loginPage("/loginForm")
						.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
								.userService(principalOauth2UserService))
								.defaultSuccessUrl("/",true));

		return http.build();
	}
}
