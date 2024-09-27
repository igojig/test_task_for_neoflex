package ru.neoflex.vacation_pay_calculator.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * класс представляющий ответ сервиса в случае возникновения исключения
 * {@code MethodArgumentTypeMismatchException} - ошибка приведения типа параметра запроса
 */
@Getter
@AllArgsConstructor
@Builder
public class ArgumentMismatchResponse {
    private String fieldName;
    private final String value;
    private final String message;
}
