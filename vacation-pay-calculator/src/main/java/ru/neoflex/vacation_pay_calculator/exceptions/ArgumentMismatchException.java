package ru.neoflex.vacation_pay_calculator.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArgumentMismatchException extends RuntimeException{
    private String message;
}
