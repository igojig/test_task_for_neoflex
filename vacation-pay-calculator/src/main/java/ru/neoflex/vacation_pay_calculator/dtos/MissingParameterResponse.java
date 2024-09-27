package ru.neoflex.vacation_pay_calculator.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * класс представляющий ответ сервиса в случае возникновения исключения
 * {@code MissingServletRequestParameterException} - когда не указан параметр запроса
 */
@Getter
@AllArgsConstructor
@Builder
public class MissingParameterResponse {
    private final int status;
    private final String fieldName;
    private  final String message;
}
