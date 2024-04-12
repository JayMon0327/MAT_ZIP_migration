package MATZIP_ver3.config.oauth;

import MATZIP_ver3.config.oauth.provider.OAuth2UserInfo;
import MATZIP_ver3.domain.Member;
import MATZIP_ver3.domain.status.Role;
import MATZIP_ver3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuthMemberService {

    private final MemberRepository memberRepository;

    private static final String userNamingWord = "_";
    private static final int defaultPoint = 0;

    public Member SignUpOAuth2(OAuth2UserInfo oAuth2UserInfo) {
        Optional<Member> userProvider = memberRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(),
                oAuth2UserInfo.getProviderId());

        if (userProvider.isPresent()) {
            Member member = userProvider.get();
            member.updateEmail(oAuth2UserInfo.getEmail());
            return memberRepository.save(member);
        } else {
            Member member = Member.builder()
                    .username(oAuth2UserInfo.getProvider() + userNamingWord + oAuth2UserInfo.getProviderId())
                    .nickName(oAuth2UserInfo.getProvider() + userNamingWord + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .point(defaultPoint)
                    .role(Role.USER)
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            return memberRepository.save(member);
        }
    }
}
