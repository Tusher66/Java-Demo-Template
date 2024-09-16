package com.greenish.web_portal.exception.handler;


import com.greenish.web_portal.exception.*;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    // handling resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request) {
        System.out.println("\n" + "ResourceNotFoundException Occurred at: " + LocalDateTime.now());
        exception.printStackTrace();
        ErrorDetails errorDetails = getErrorDetails(exception.getMessage(), request, exception.getErrorDetails());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // handling no data found exception
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<?> noDataFoundExceptionHandler(NoDataFoundException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), exception.getErrorDetails());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // handling argumentNotValid exception
    @ExceptionHandler(ArgumentNotValidException.class)
    public ResponseEntity<?> argumentNotValidHandling(ArgumentNotValidException exception, WebRequest request) {
        System.out.println("\n" + "ArgumentNotValidException Occurred at: " + LocalDateTime.now());
        exception.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), exception.getErrorDetails());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // handling field exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        System.out.println("\n" + "MethodArgumentNotValidException Occurred at: " + LocalDateTime.now());
        ex.printStackTrace();
        ObjectError objectError = ex.getBindingResult().getAllErrors().stream().findFirst().orElse(null);
        // String fieldName = objectError != null ? ((FieldError) objectError).getField() : "field";
        String message = objectError != null ? objectError.getDefaultMessage() : "Invalid request";
        // ErrorDetails errorDetails = new ErrorDetails(new Date(), "[" + fieldName + "]: value is invalid", message);
        ErrorDetails errorDetails = new ErrorDetails(message);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // handling global exception
    @ExceptionHandler(CommonServerException.class)
    public ResponseEntity<?> serverExceptionHandling(CommonServerException exception, WebRequest request) {
        System.out.println("\n" + "CommonServerException Occurred at: " + LocalDateTime.now());
        exception.printStackTrace();
        // ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        ErrorDetails errorDetails = getErrorDetails(exception.getMessage(), request, exception.getErrorDetails());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handling usernameNotFound exception
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundExceptionHandling(UsernameNotFoundException exception) {
        System.out.println("\n" + "UsernameNotFoundException Occurred at: " + LocalDateTime.now());
        exception.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptionHandling(BadCredentialsException exception) {
        System.out.println("\n" + "BadCredentialsException Occurred at: " + LocalDateTime.now());
        exception.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails("INVALID USERNAME OR PASSWORD");
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<?> unauthorizedUserExceptionHandling(UnauthorizedUserException exception) {
        System.out.println("\n" + "UnauthorizedUserException Occurred at: " + LocalDateTime.now());
        exception.printStackTrace();
        ErrorDetails errorDetails = exception.getCode() != null ? new ErrorDetails(exception.getMessage(), exception.getCode())
                : new ErrorDetails(exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<?> customUnauthorizedExceptionHandling(CustomUnauthorizedException exception) {
        System.out.println("\n" + "CustomUnauthorizedException Occurred at: " + LocalDateTime.now());
        exception.printStackTrace();
        ErrorDetails errorDetails = exception.getCode() != null ? new ErrorDetails(exception.getMessage(), exception.getCode())
                : new ErrorDetails(exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> dateTimeParseExceptionHandling(Exception ex, WebRequest request) {
        System.out.println("\n" + "DateTimeParseException Occurred at: " + LocalDateTime.now());
        ex.printStackTrace();
        ErrorDetails errorDetails = getErrorDetails(ex.getMessage(), request, ex.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handling crud exception
    @ExceptionHandler(CrudException.class)
    public ResponseEntity<?> crudExceptionHandler(CrudException ex, WebRequest request) {
        System.out.println("\n" + "CrudException Occurred at: " + LocalDateTime.now());
        ex.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), ex.getErrorDetails());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handling NumberFormatException
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> numberFormatExceptionHandler(MethodArgumentTypeMismatchException ex, WebRequest request) {
        System.out.println("\n" + "MethodArgumentTypeMismatchException Occurred at: " + LocalDateTime.now());
        ex.printStackTrace();
        String details = "[Field: " + ex.getName() + "], [Error: " + ex.getMessage() + "]";
        ErrorDetails errorDetails = getErrorDetails("INVALID REQUEST!", request, details);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // handling file upload exception
   /* @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<?> fileUploadExceptionHandler(FileUploadException ex, WebRequest request) {
        System.out.println("\n" + "FileUploadException Occurred at: " + LocalDateTime.now());
        ex.printStackTrace();
        ErrorDetails errorDetails = getErrorDetails(ex.getMessage(), request, ex.getErrorDetails());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }*/

    // handling bind exception
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindExceptionHandling(BindException ex, WebRequest request) {
        System.out.println("\n" + "BindException Occurred at: " + LocalDateTime.now());
        ex.printStackTrace();
        String defaultMessage = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        defaultMessage = (!StringUtils.hasText(defaultMessage)) ? "REQUIRED FIELD CAN'T BE EMPTY!" : defaultMessage;
        ErrorDetails errorDetails = getErrorDetails(defaultMessage, request, ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // handling global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception ex, WebRequest request) {
        // ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        System.out.println("\n" + "EXCEPTION OCCURRED AT: " + LocalDateTime.now());
        ex.printStackTrace();
        ErrorDetails errorDetails = getErrorDetails("SOMETHING WENT WRONG! PLEASE TRY AGAIN LATER.", request, ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDetails getErrorDetails(String description, WebRequest request, String details) {
        String additionalDesc = details + " [" + request.getDescription(false) + "]";
        return new ErrorDetails(description, additionalDesc);
    }

}
