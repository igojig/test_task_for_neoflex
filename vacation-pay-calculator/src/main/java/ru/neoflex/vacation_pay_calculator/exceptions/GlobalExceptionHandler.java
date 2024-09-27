package ru.neoflex.vacation_pay_calculator.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.neoflex.vacation_pay_calculator.dtos.ArgumentMismatchResponse;
import ru.neoflex.vacation_pay_calculator.dtos.MissingParameterResponse;
import ru.neoflex.vacation_pay_calculator.dtos.ValidationErrorResponse;
import ru.neoflex.vacation_pay_calculator.dtos.Violation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * обработчик исключений при валидации параметров запроса
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse constraintValidationException(ConstraintViolationException e) {

        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> Violation.builder()
                                .fieldName(violation.getPropertyPath().toString())
                                .invalidValue(violation.getInvalidValue().toString())
                                .message(violation.getMessage())
                                .build()
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MissingParameterResponse> missingServletRequestParameterException(MissingServletRequestParameterException e) {

        MissingParameterResponse missingParameterResponse = MissingParameterResponse.builder()
                .fieldName(e.getParameterName())
                .message(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(missingParameterResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ArgumentMismatchResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        ArgumentMismatchResponse response = ArgumentMismatchResponse.builder()
                .fieldName(e.getPropertyName())
                .value(Objects.requireNonNull(e.getValue()).toString())
                .message(e.getMessage())
                .build();


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


}
