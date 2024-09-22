package ru.neoflex.vacation_pay_calculator.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VacationPayCalculatorResponse {
    private final String message;
    private final String vacationPayWithoutNdfl;
}
