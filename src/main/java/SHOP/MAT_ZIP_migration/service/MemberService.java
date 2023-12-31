package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.status.Role;
import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.dto.member.JoinMemberDto;
import SHOP.MAT_ZIP_migration.dto.member.UpdateMemberDto;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import SHOP.MAT_ZIP_migration.service.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {

    private static final Integer defaultPoint = 0;

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    @Transactional
    public void SignUp(JoinMemberDto joinMemberDto) {
        memberValidator.DuplicatedJoinMember(joinMemberDto.getUsername(), joinMemberDto.getEmail());
        String endPassword = memberValidator.PasswordCheck(joinMemberDto.getPassword(),
                joinMemberDto.getPasswordCheck());

        Member member = Member.builder()
                .username(joinMemberDto.getUsername())
                .password(endPassword)
                .nickName(joinMemberDto.getNickName())
                .email(joinMemberDto.getEmail())
                .address(joinMemberDto.getAddress())
                .role(Role.USER)
                .point(defaultPoint)
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public void update(UpdateMemberDto dto) {
        Member persistance = memberRepository.findById(dto.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패");
        });

        if (persistance.getProvider() == null || persistance.getProvider().equals("")) {
            String encodePassword = memberValidator.PasswordCheck(dto.getPassword(),
                    dto.getPasswordCheck());
            persistance.updateMember(encodePassword, dto.getNickName(), dto.getEmail(), dto.getAddress());
        }
    }
}
