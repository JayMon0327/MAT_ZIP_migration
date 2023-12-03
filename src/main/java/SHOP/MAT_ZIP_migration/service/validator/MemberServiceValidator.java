package SHOP.MAT_ZIP_migration.service.validator;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceValidator {

    private final MemberRepository memberRepository;

    public void DuplicatedJoinMember(String username, String email) {
        if (memberRepository.existsByUsername(username)) {
            throw new CustomException(CustomErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(CustomErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }
}
