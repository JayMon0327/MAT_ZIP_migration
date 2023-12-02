package SHOP.MAT_ZIP_migration.exception;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CustomErrorCode {
    USERNAME_ALREADY_EXISTS("ERP201", "유저네임이 중복입니다."),
    EMAIL_ALREADY_EXISTS("ERP202", "이메일이 중복입니다.");

    private final String errorCode;
    private final String errorMessage;
}
