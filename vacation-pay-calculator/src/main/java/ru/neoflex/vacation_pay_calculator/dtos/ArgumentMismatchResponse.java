package ru.neoflex.vacation_pay_calculator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * класс описывающий состояние ошибки в случае возникновения исключения
 * {@code ArgumentMismatchException} - ошибка приведения параметра запроса в число
 */
@Data
@AllArgsConstructor
public class ArgumentMismatchResponse {
    private final int errorCode;
    private final String message;
}
