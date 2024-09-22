package ru.neoflex.vacation_pay_calculator.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.neoflex.vacation_pay_calculator.dtos.ArgumentMismatchResponse;
import ru.neoflex.vacation_pay_calculator.dtos.ValidationErrorResponse;
import ru.neoflex.vacation_pay_calculator.dtos.Violation;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     *
     * обработчик исключений при приведении параметров запроса в числа
     */
    @ExceptionHandler(ArgumentMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ArgumentMismatchResponse> mismatchException(ArgumentMismatchException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ArgumentMismatchResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    /**
     *
     * обработчик исключений при валидации параметров запроса
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse constraintValidationException(ConstraintViolationException e) {

        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
}
