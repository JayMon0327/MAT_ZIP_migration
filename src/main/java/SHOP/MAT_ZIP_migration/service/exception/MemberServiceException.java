package SHOP.MAT_ZIP_migration.service.exception;

import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberServiceException {

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
