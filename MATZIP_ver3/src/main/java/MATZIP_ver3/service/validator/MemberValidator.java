package MATZIP_ver3.service.validator;

import MATZIP_ver3.domain.Member;
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

    public void DuplicatedJoinMember(String username, String email) {
        if (memberRepository.existsByUsername(username)) {
            throw new CustomException(CustomErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(CustomErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

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
     * 아래 메서드 4개는 비밀번호 관련
     */
    public void passwordCheck(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_EQUAL);
        }
    }

    public void validatePasswordChange(PasswordDto dto) {
        validateProviderMember(dto.getId());
        validateCurrentPassword(dto.getId(), dto.getCurrentPassword());
        passwordCheck(dto.getNewPassword(), dto.getNewPasswordCheck());
    }

    public void validateCurrentPassword(Long userId, String currentPassword) {
        Optional<Member> member = memberRepository.findById(userId);
        if (member.isPresent()) {
            if (!member.get().getPassword().equals(currentPassword)) {
                throw new CustomException(CustomErrorCode.PASSWORD_WRONG);
            }
        }
    }
    //일반 유저인지 체크, Oauth유저는 비밀번호 변경 불가
    public void validateProviderMember(Long userId) {
        Optional<Member> member = memberRepository.findById(userId);
        if (member.isPresent()) {
            if (member.get().getProvider() != null || !member.get().getProvider().equals("")) {
                throw new CustomException(CustomErrorCode.OAUTH_MEMBER_CANNOT_CHANGE);
            }
        }
    }


}
