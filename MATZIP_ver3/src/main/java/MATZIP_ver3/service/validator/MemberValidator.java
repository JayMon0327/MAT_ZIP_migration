package MATZIP_ver3.service.validator;

import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import MATZIP_ver3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String PasswordCheck(String password, String passwordCheck) {
        if (password.equals(passwordCheck)) {
            return encoder.encode(password);
        }
        throw new CustomException(CustomErrorCode.PASSWORD_NOT_EQUAL);
    }
}
