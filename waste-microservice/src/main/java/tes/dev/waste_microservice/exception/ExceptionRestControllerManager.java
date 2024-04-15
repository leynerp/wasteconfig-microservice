package tes.dev.waste_microservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import tes.dev.waste_microservice.config.ExceptionResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionRestControllerManager {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerRecourseNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        Map<String, String> errorsMap = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String key = ((FieldError) error).getField();
            String value = error.getDefaultMessage();
            errorsMap.put(key, value);
        });
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorsMap.toString(), webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handlerConstraintViolationException(ConstraintViolationException ex, WebRequest webRequest) {
        Map<String, String> errorsMap = new HashMap<>();
       ex.getConstraintViolations()
                .stream()
                .forEach(constraintViolation -> {
                    int  lastIndex = constraintViolation.getPropertyPath().toString().lastIndexOf(".");
                    String key = constraintViolation.getPropertyPath().toString().substring(lastIndex + 1);
                    String value = constraintViolation.getMessage();
                    errorsMap.put(key, value);
                });
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorsMap.toString(), webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handlerBadRequestException(BadRequestException ex, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handlerExceptionGeneral(BadRequestException ex, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
}
