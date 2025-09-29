package com.SlotSync.SlotSync.Utilities;

import com.SlotSync.SlotSync.Exception.JobPortalException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.env.Environment;              // FIX: correct Environment import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private Environment environment;

    // Handles validation exceptions (@Valid / @Validated)
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception) {
        String msg = "";

        if (exception instanceof MethodArgumentNotValidException ex) {
            msg = ex.getBindingResult()                 // FIX: getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        } else {
            ConstraintViolationException ex = (ConstraintViolationException) exception;
            msg = ex.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
        }

        ErrorInfo errorInfo = new ErrorInfo(
                msg,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JobPortalException.class)
    public ResponseEntity<ErrorInfo> generalException(JobPortalException exception) {
        ErrorInfo errorInfo = new ErrorInfo(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Fallback for any other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalException(Exception exception) {
        String msg = environment.getProperty(exception.getMessage());  // FIX: semicolon + property lookup
        if (msg == null || msg.isBlank()) {
            msg = exception.getMessage();
        }

        ErrorInfo errorInfo = new ErrorInfo(
                msg,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
