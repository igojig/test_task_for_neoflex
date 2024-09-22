package ru.neoflex.vacation_pay_calculator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * класс, описывающий ошибку валидации
 */
@Getter
@AllArgsConstructor
public class Violation {
    private final String fieldName;
    private final String message;
}
