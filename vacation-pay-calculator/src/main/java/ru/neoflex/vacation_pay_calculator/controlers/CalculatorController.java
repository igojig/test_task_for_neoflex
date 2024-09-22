package ru.neoflex.vacation_pay_calculator.controlers;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.vacation_pay_calculator.dtos.VacationPayCalculatorResponse;
import ru.neoflex.vacation_pay_calculator.exceptions.ArgumentMismatchException;
import ru.neoflex.vacation_pay_calculator.services.CalculatorService;

import java.math.BigDecimal;

@Slf4j
@Validated
@RestController
@RequestMapping("/calculacte") //так написано в задании
@AllArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    /**
     *
     * @param averageSalary  среняя зарплата за 12 месяцев
     * @param vacationDays  кол-во дней отпуска
     * @return  начисленные отпускные
     */

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacationPayCalculatorResponse> calculate(
            @RequestParam(value = "averageSalary", required = false)
            @NotNull(message = "параметр averageSalary(средняя зарплата) должен быть задан")
            String averageSalary,

            @RequestParam(value = "vacationDays", required = false)
            @NotNull(message = "параметр vacationDays(кол-во дней отпуска) должен быть задан")
            String vacationDays
    ) {

        String message = String.format("Средняя зп: [%s], дней отпуска: [%s]", averageSalary, vacationDays);

        BigDecimal averageSalaryDecimal=getBigDecimal(averageSalary);;
        Long vacationDaysDecimal=getLong(vacationDays);

        BigDecimal total = calculatorService.calculate(averageSalaryDecimal, vacationDaysDecimal);

        return new ResponseEntity<>(VacationPayCalculatorResponse.builder()
                .message(message)
                .vacationPayWithoutNdfl(total.toString())
                .build(), HttpStatus.OK);

    }

    private  Long getLong(String vacationDays) {
        long vacationDaysDecimal;
        try {
            vacationDaysDecimal= Long.parseLong(vacationDays);
        } catch (NumberFormatException e){
            throw new ArgumentMismatchException(String.format("Параметр vacationDays:[%s] задан неверно. " +
                    "Ожидается целое число", vacationDays));
        }
        return vacationDaysDecimal;
    }

    private  BigDecimal getBigDecimal(String averageSalary) {
        BigDecimal averageSalaryDecimal;
        try{
            averageSalaryDecimal=new BigDecimal(averageSalary);
        } catch (NumberFormatException e){
            throw new ArgumentMismatchException(String.format("Параметр averageSalary:[%s] задан неверно. " +
                    "Ожидается число с десятичной точкой и не более 2 десятичных знаков.", averageSalary));
        }
        return averageSalaryDecimal;
    }
}
