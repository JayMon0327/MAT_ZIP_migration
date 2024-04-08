package MATZIP_ver3.service;

import MATZIP_ver3.domain.status.Role;
import MATZIP_ver3.domain.Member;
import MATZIP_ver3.dto.member.JoinMemberDto;
import MATZIP_ver3.dto.member.PasswordDto;
import MATZIP_ver3.dto.member.UpdateMemberDto;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import MATZIP_ver3.repository.MemberRepository;
import MATZIP_ver3.service.validator.MemberValidator;
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
    public void signUp(JoinMemberDto dto) {
        String encodedPassword = memberValidator.validateSignup(dto);

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(encodedPassword)
                .nickName(dto.getNickName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .role(Role.USER)
                .point(defaultPoint)
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public void update(UpdateMemberDto dto) {
        Member member = memberRepository.findById(dto.getId()).orElseThrow(() -> {
            throw new CustomException(CustomErrorCode.NOT_FOUND_MEMBER);
        });
        memberValidator.validateEmailUpdate(dto.getId(), dto.getEmail());
        member.updateMember(dto.getNickName(), dto.getEmail(), dto.getAddress());
    }

    @Transactional
    public void updatePassword(PasswordDto dto) {
        Member member = memberRepository.findById(dto.getId()).orElseThrow(() -> {
            throw new CustomException(CustomErrorCode.NOT_FOUND_MEMBER);
        });
        String encodedPassword = memberValidator.validatePasswordChange(dto);
        member.updatePassword(encodedPassword);
    }
}
