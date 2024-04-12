package MATZIP_ver3.service.validator;

import MATZIP_ver3.domain.Member;
import MATZIP_ver3.dto.member.JoinMemberDto;
import MATZIP_ver3.dto.member.PasswordDto;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import MATZIP_ver3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberValidator {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public String validateSignup(JoinMemberDto dto) {
        duplicatedJoinMember(dto.getUsername(), dto.getEmail());
        passwordCheck(dto.getPassword(), dto.getPasswordCheck());
        return encoder.encode(dto.getPassword());
    }

    public void duplicatedJoinMember(String username, String email) {
        if (memberRepository.existsByUsername(username)) {
            throw new CustomException(CustomErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(CustomErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    public void passwordCheck(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_EQUAL);
        }
    }

    /**
     * 이메일 변경 시 검증
     */
    public void validateEmailUpdate(Long userId, String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            // 이메일이 현재 로그인한 사용자의 것이 아닌 다른 사용자의 것인지 확인
            if (!member.get().getId().equals(userId)) {
                throw new CustomException(CustomErrorCode.EMAIL_ALREADY_EXISTS);
            }
        }
    }

    /**
     * 비밀번호 변경 관련 아래 3개
     */
    public String validatePasswordChange(PasswordDto dto) {
        validateProviderMember(dto.getId());
        validateCurrentPassword(dto.getId(), dto.getCurrentPassword());
        passwordCheck(dto.getNewPassword(), dto.getNewPasswordCheck());
        return encoder.encode(dto.getNewPassword());
    }

    public void validateCurrentPassword(Long userId, String currentPassword) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 찾기 실패"));
        // 입력된 현재 비밀번호와 저장된 비밀번호 해시가 일치하는지 확인
        if (!encoder.matches(currentPassword, member.getPassword())) {
            throw new CustomException(CustomErrorCode.NOT_EQUAL_PASSWORD);
        }
    }

    //일반 유저인지 체크, Oauth유저는 비밀번호 변경 불가
    public void validateProviderMember(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException(CustomErrorCode.NOT_FOUND_MEMBER);
        });
        if (member.getProvider() != null && !member.getProvider().isEmpty()) {
            throw new CustomException(CustomErrorCode.OAUTH_MEMBER_CANNOT_CHANGE);
        }
    }
}
