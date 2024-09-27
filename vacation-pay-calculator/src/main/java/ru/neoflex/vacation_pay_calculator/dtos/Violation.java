package ru.neoflex.vacation_pay_calculator.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * класс, описывающий ошибку валидации
 */
@Getter
@AllArgsConstructor
@Builder
public class Violation {
    private final String fieldName;
    private final String invalidValue;
    private final String message;
}
