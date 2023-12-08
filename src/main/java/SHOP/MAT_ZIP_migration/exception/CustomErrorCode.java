package SHOP.MAT_ZIP_migration.exception;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CustomErrorCode {
    USERNAME_ALREADY_EXISTS("ERP201", "유저네임이 중복입니다."),
    EMAIL_ALREADY_EXISTS("ERP202", "이메일이 중복입니다."),
    PASSWORD_NOT_EQUAL("ERP203", "비밀번호를 확인 해주세요"),
    FILE_PROCESSING_ERROR("ERP204", "파일 처리 중 오류가 발생했습니다."),
    FILE_URL_ERROR("ERP205", "파일 처리 중 오류가 발생했습니다.");

    private final String errorCode;
    private final String errorMessage;
}
