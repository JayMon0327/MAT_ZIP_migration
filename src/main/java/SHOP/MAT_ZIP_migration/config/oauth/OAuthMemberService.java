package SHOP.MAT_ZIP_migration.config.oauth;

import SHOP.MAT_ZIP_migration.config.oauth.provider.OAuth2UserInfo;
import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.status.Role;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuthMemberService {

    private final MemberRepository memberRepository;

    public Member SignUpOAuth2(OAuth2UserInfo oAuth2UserInfo) {
        Optional<Member> userProvider = memberRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(),
                oAuth2UserInfo.getProviderId());

        if (userProvider.isPresent()) {
            Member member = userProvider.get();
            member.updateEmail(oAuth2UserInfo.getEmail());
            return memberRepository.save(member);
        } else {
            Member member = Member.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .role(Role.USER)
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            return memberRepository.save(member);
        }
    }
}
