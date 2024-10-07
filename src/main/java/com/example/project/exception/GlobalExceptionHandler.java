package com.example.project.exception;

import com.example.project.dto.response.CustomError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleException(Exception ex, WebRequest request) {
        CustomError errorResponse = new CustomError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "") // Extracting the path
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        CustomError errorResponse = new CustomError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                validationErrors.toString(), // Combine the validation errors into a single message
                request.getDescription(false).replace("uri=", "") // Extracting the path
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CustomError> handleJwtException(JwtException ex, WebRequest request) {
        CustomError errorResponse = new CustomError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "JWT Error",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "") // Extracting the path
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<CustomError> handleSignatureException(SignatureException ex, WebRequest request) {
        CustomError errorResponse = new CustomError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Invalid JWT Signature",
                "The JWT signature is invalid",
                request.getDescription(false).replace("uri=", "") // Extracting the path
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CustomError> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        CustomError errorResponse = new CustomError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "JWT Token Expired",
                "The JWT token has expired",
                request.getDescription(false).replace("uri=", "") // Extracting the path
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomError> handleBadCredentialException(BadCredentialsException ex, WebRequest request) {
        CustomError errorResponse = new CustomError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Bad Credentials",
                "The username or password is incorrect",
                request.getDescription(false).replace("uri=", "") // Extracting the path
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<CustomError> handleAccountStatusException(AccountStatusException ex, WebRequest request) {
        CustomError errorResponse = new CustomError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Account Locked",
                "The account is locked",
                request.getDescription(false).replace("uri=", "") // Extracting the path
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomError> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        CustomError errorResponse = new CustomError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Unauthorized",
                "You are not authorized to access this resource",
                request.getDescription(false).replace("uri=", "") // Extracting the path
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
