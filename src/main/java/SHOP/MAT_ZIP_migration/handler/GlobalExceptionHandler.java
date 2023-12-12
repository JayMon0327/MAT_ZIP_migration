package SHOP.MAT_ZIP_migration.handler;

import SHOP.MAT_ZIP_migration.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    /**
     * validation 어노테이션 검증
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> {
                    errors.put(((FieldError) c).getField(), getErrorMessage(c));
                });
        log.info("검증오류 발생: "+ errors);
        return ResponseEntity.badRequest().body(errors);
    }

    private String getErrorMessage(ObjectError error) {
        String[] codes = error.getCodes();
        for (String code : codes) {
            try {
                return messageSource.getMessage(code, error.getArguments(), Locale.KOREA);
            } catch (NoSuchMessageException ignored) {}
        }
        return error.getDefaultMessage();
    }

    /**
     * 타입 미스매치 검증
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchExceptions(HttpMessageNotReadableException ex){
        Map<String, String> errors = new HashMap<>();

        Pattern errorFieldPattern = Pattern.compile("\\[[\"](.*?)[\"]\\]");
        Matcher errorFieldMatcher = errorFieldPattern.matcher(ex.getCause().getMessage());
        String errorField = errorFieldMatcher.find() ? errorFieldMatcher.group(1) : "FAIL";

        Pattern rightTypePattern = Pattern.compile("[`](.*?)[`]");
        Matcher rightTypeMatcher = rightTypePattern.matcher(ex.getMessage());
        String rightType = rightTypeMatcher.find() ? rightTypeMatcher.group(1) : "?";

        String errorMessage = messageSource.getMessage("typeMismatch", new Object[] {rightType}, Locale.KOREA);
        errors.put(errorField, errorMessage);

        log.error(ex.toString());

        return ResponseEntity.badRequest().body(errors);
    }


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
