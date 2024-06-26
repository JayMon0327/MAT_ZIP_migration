package MATZIP_ver3.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CustomErrorCode {
    USERNAME_ALREADY_EXISTS("ERP201", "유저네임이 중복입니다."),
    EMAIL_ALREADY_EXISTS("ERP202", "이메일이 중복입니다."),
    PASSWORD_NOT_EQUAL("ERP203", "비밀번호를 확인 해주세요"),
    NOT_EQUAL_PASSWORD("ERP204", "현재 비밀번호가 일치하지 않습니다."),
    OAUTH_MEMBER_CANNOT_CHANGE("ERP205", "Oauth회원은 비밀번호 변경이 불가능합니다"),
    NOT_FOUND_MEMBER("ERP206", "회원을 찾을 수 없습니다."),
    FILE_PROCESSING_ERROR("ERP301", "파일 처리 중 오류가 발생했습니다."),
    FILE_URL_ERROR("ERP302", "파일 URL 오류가 발생했습니다."),
    NOT_FOUND_ITEM("ERP400", "상품을 찾을 수 없습니다."),
    NOT_FOUND_ORDER("ERP401", "주문을 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT("ERP402", "상품 게시글을 찾을 수 없습니다."),
    NOT_ORDER_MEMBER("ERP403", "상품을 구매한 고객이 아닙니다."),
    NOT_EQUAL_FINAL_PRICE("ERP500", "결제금액이 일치하지 않습니다."),
    NOT_ENOUGH_STOCK("ERP501", "재고가 부족합니다."),
    NOT_ENOUGH_POINT("ERP502", "포인트가 부족합니다."),
    NOT_EQUAL_VERIFY_PRICE("ERP503", "지불 결제금액이 일치하지 않습니다."),
    NOT_NORMAL_PAYMENT_ORDER("ERP504", "주문되지 않은 상품입니다."),
    ALREADY_DELIVERY_STATUS("ERP505", "이미 배송된 상품입니다"),
    FAIL_BRING_ACCESS_TOKEN("ERP700", "API 액세스 토큰을 발급받는데 실패하였습니다."),
    FAIL_FIND_PAYMENT_DETAIL("ERP701", "API 결제 조회에 실패하였습니다.");

    private final String errorCode;
    private final String errorMessage;
}
