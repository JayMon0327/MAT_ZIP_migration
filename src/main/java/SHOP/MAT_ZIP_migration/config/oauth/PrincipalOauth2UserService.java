package SHOP.MAT_ZIP_migration.config.oauth;

import java.util.Map;
import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.config.oauth.provider.*;
import SHOP.MAT_ZIP_migration.domain.Member;
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

	private final OAuthMemberService oAuthMemberService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		OAuth2UserInfo oAuth2UserInfo = null;
		String registrationId = userRequest.getClientRegistration().getRegistrationId(); //provider
		return processOAuth2User(registrationId, oAuth2User,oAuth2UserInfo);
	}

	/**
	 * 위에서 받은 userRequest정보에 userRequest.getAttibutes를 이용해서 로그인 요청과 자동회원가입을 한다.
	 */
	private OAuth2User processOAuth2User(String registrationId, OAuth2User oAuth2User, OAuth2UserInfo oAuth2UserInfo) {
		if (registrationId.equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		}
		if (registrationId.equals("facebook")) {
			oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
		}
		if (registrationId.equals("naver")){
			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		}
		if (registrationId.equals("kakao")){
			oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("kakao_account"),
					String.valueOf(oAuth2User.getAttributes().get("id")));
		}

		Member member = oAuthMemberService.SignUpOAuth2(oAuth2UserInfo);
		return new PrincipalDetails(member, oAuth2User.getAttributes());
	}
}
