package ru.neoflex.vacation_pay_calculator.services;

import java.math.BigDecimal;

public interface CalculatorService {
    BigDecimal calculate(BigDecimal averageSalary, Long vacationDays);
}
