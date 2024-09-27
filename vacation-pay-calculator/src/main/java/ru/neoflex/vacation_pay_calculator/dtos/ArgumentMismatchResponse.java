package ru.neoflex.vacation_pay_calculator.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * класс представляющий ответ сервиса в случае возникновения исключения
 * {@code MethodArgumentTypeMismatchException} - ошибка приведения типа параметра запроса
 */
@Getter
@AllArgsConstructor
@Builder
public class ArgumentMismatchResponse {
    private final int status;
    private String fieldName;
    private final String invalidValue;
    private final String message;
}
