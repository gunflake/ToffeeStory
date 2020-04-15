package com.toffeestory.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.ResponseEntity.badRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {InvalidAccountException.class})
    public ResponseEntity invalidAccount(InvalidAccountException ex, WebRequest request) {
        log.error("handling NotValidAccountException...");
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity notFoundAccount(InvalidAccountException ex, WebRequest request) {
        log.error("handling InvalidAccountException...");
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity constraintViolation(ConstraintViolationException ex, WebRequest request){
        String message = ex.getConstraintName().substring(ex.getConstraintName().lastIndexOf('_')+1) +"이 이미 사용중입니다.";
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(value = {InvalidImageException.class})
    public ResponseEntity invalidImage(InvalidImageException ex, WebRequest request){
        log.error("handling InvalidImageException...");
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request){
        log.error("handling InvalidJwtAuthenticationException...");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestApiError(HttpStatus.UNAUTHORIZED, "로그인 세션이 만료되었습니다. 다시 로그인 해주세요."));
    }

    @ExceptionHandler(value = {NotFoundPostException.class})
    public ResponseEntity notFoundPostException(NotFoundPostException ex){
        log.error(ex.getMessage());
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {NotFoundImageException.class})
    public ResponseEntity notFoundImageException(NotFoundImageException ex){
        log.error(ex.getMessage());
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    public ResponseEntity maxUploadSizeExceededException(MaxUploadSizeExceededException ex){
        log.error(ex.getMessage());
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {EmailSendException.class})
    public ResponseEntity emailSendException(EmailSendException ex){
        log.error(ex.getMessage());
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {InvalidPasswordTokenException.class})
    public ResponseEntity notFoundPasswordToken(InvalidPasswordTokenException ex){
        log.error(ex.getMessage());
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {NotfoundEmailException.class})
    public ResponseEntity notfoundEmailException(NotfoundEmailException ex){
        log.error(ex.getMessage());
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

}
