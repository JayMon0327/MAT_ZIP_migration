package SHOP.MAT_ZIP_migration.handler;

import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseDto<String> handleCustomException(CustomException ex) {
        return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getErrorMessage());
    }
}
