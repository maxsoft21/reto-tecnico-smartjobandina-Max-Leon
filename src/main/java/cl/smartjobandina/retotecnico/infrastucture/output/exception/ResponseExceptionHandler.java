package cl.smartjobandina.retotecnico.infrastucture.output.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {

        ErrorInfo error = new ErrorInfo();
        error.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {

        ErrorInfo error = new ErrorInfo();
        error.setMessage(ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", ")));

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<Object> handleValidateException(ValidateException ex) {

        ErrorInfo error = new ErrorInfo();
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGeneralException(Exception ex) {
        ErrorInfo errorDetails = new ErrorInfo();
        errorDetails.setMessage("Internal Server Error - Se presentó una condición inesperada que impidió completar el Request");
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
