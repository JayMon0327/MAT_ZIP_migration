package SHOP.MAT_ZIP_migration.handler;

import SHOP.MAT_ZIP_migration.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.ObjectError;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.info("검증에러 캐치 " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
//                .map(ObjectError::getDefaultMessage)
//                .map(code -> messageSource.getMessage(code, null, LocaleContextHolder.getLocale()))
//                .collect(Collectors.toList());
//
//        log.info("검증 오류 발생: " + errorMessages);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
//    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomException(CustomException ex) {
        log.info("사용자 정의 예외 발생: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity IllegalArgumentExceptions(IllegalArgumentException ex) {
        log.info("IllegalArgument 캐치 " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
