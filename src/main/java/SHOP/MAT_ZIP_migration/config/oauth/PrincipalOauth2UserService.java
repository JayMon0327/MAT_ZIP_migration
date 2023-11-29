package SHOP.MAT_ZIP_migration.config.oauth;

import java.util.Map;
import java.util.Optional;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.config.oauth.provider.*;
import SHOP.MAT_ZIP_migration.model.Role;
import SHOP.MAT_ZIP_migration.model.User;
import SHOP.MAT_ZIP_migration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("Principal 객체생성 완료");
		OAuth2User oAuth2User = super.loadUser(userRequest);
		return processOAuth2User(userRequest, oAuth2User);
	}


}
