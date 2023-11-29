package SHOP.MAT_ZIP_migration.config.oauth.provider;

import java.util.Map;

/**
 * 카카오 로그인 시 사용자 정보 응답 값에 id가 분리되어 있으므로 id 필드 생성
 * 사용자 정보 응답 값 = kakao_account
 */
public class KakaoUserInfo implements OAuth2UserInfo{

    private String id;
	private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes,String id) {
        this.attributes = attributes;
        this.id = id;
    }

    @Override
    public String getProviderId() {
        return id;
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

	@Override
	public String getProvider() {
		return "kakao";
	}

}
