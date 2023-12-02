package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Role;
import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.dto.JoinMemberDto;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void 회원가입(JoinMemberDto joinMemberDto) {
        String endPassword = encoder.encode(joinMemberDto.getPassword());
        Member member = Member.builder()
                .username(joinMemberDto.getUsername())
                .password(endPassword)
                .email(joinMemberDto.getEmail())
                .role(Role.USER)
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public void 회원수정(Member member) {
        //수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고(select으로 먼저불러옴으로써 영속화시킴), 영속화된 User오브젝트를 수정
        //select을 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서 !!
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주거든요.
        Member persistance = memberRepository.findById(member.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });

        //validation -> OAuth 필드에 값이 없으면 수정가능
        if (persistance.getProvider() == null || persistance.getProvider().equals("")) {
            String rawPassword = member.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            persistance.setEmail(member.getEmail());
        }

        // 회원 수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 이 자동으로 됩니다.
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.

    }

    public Member 회원찾기(String username) {
        Member member = memberRepository.findByUsername(username); // 값이 없을 경우에 대한 예외처리 추가해야함
        return member;
    }
}
