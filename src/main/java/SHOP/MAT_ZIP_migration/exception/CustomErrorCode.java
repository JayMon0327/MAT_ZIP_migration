package SHOP.MAT_ZIP_migration.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CustomErrorCode {
    USERNAME_ALREADY_EXISTS("ERP201", "유저네임이 중복입니다."),
    EMAIL_ALREADY_EXISTS("ERP202", "이메일이 중복입니다."),
    PASSWORD_NOT_EQUAL("ERP203", "비밀번호를 확인 해주세요"),
    FILE_PROCESSING_ERROR("ERP301", "파일 처리 중 오류가 발생했습니다."),
    FILE_URL_ERROR("ERP302", "파일 URL 오류가 발생했습니다."),
    NOT_FOUND_ITEM("ERP400", "상품을 찾을 수 없습니다."),
    NOT_FOUND_ORDER("ERP401", "주문을 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT("ERP402", "상품 게시글을 찾을 수 없습니다."),
    NOT_EQUAL_FINAL_PRICE("ERP500", "최종 결제금액이 일치하지 않습니다."),
    NOT_ENOUGH_STOCK("ERP501", "재고가 부족합니다."),
    NOT_ENOUGH_POINT("ERP502", "포인트가 부족합니다.");


    private final String errorCode;
    private final String errorMessage;
}
