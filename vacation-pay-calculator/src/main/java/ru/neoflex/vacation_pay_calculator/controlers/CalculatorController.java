package ru.neoflex.vacation_pay_calculator.controlers;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.vacation_pay_calculator.dtos.VacationPayCalculatorResponse;
import ru.neoflex.vacation_pay_calculator.services.CalculatorService;

import java.math.BigDecimal;

@Slf4j
@Validated
@RestController
@RequestMapping("/calculacte") //так написано в задании
@AllArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacationPayCalculatorResponse> calculate(
            @RequestParam(value = "averageSalary", required = false)
            @NotNull(message = "параметр averageSalary(средняя зарплата) должен быть задан")
            @Positive(message = "параметр averageSalary(средняя зарплата) должен быть положительным")
            BigDecimal averageSalary,

            @RequestParam(value = "vacationDays", required = false)
            @NotNull(message = "параметр vacationDays(кол-во дней отпуска) должен быть задан")
            @Min(value = 1, message = "параметр vacationDays(кол-во дней отпуска) должен быть больше 1")
            @Max(value = 28, message = "параметр vacationDays(кол-во дней отпуска) должен быть меньше или равен 28")
            Long vacationDays
    ) {

        String message = String.format("Средняя зп: [%s], дней отпуска: [%s]", averageSalary, vacationDays);
        BigDecimal total = calculatorService.calculate(averageSalary, vacationDays);

        return new ResponseEntity<>(VacationPayCalculatorResponse.builder()
                .message(message)
                .vacationPayWithoutNdfl(total.toString())
                .build(), HttpStatus.OK);

    }
}
