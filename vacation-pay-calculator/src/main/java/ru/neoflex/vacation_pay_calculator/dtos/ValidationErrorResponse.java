package ru.neoflex.vacation_pay_calculator.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * класс представляющий ответ сервиса в случае возникновения исключения
 * {@code ConstraintViolationException} - при ошибках валидации параметров запроса
 */
@Getter
@AllArgsConstructor
public class ValidationErrorResponse {
    private final int status;
    private final List<Violation> violations;
}
