package ru.neoflex.vacation_pay_calculator.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * класс, представляющий успешный ответ сервиса
 *
 */
@Data
@Builder
public class VacationPayCalculatorResponse {

    private final String message;

    /**
     * сумма начисленных отпускных за вычетом НДФЛ
     */
    private final String vacationPayWithoutNdfl;
}
