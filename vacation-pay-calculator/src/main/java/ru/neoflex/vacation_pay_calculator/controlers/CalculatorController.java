package ru.neoflex.vacation_pay_calculator.controlers;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.vacation_pay_calculator.configuration.ServiceMessages;
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
    private final ServiceMessages serviceMessages;


//    private final CountDaysWithoutHolidays countDaysWithoutHolidays;

    /**
     * @param averageSalary среняя зарплата за 12 месяцев
     * @param vacationDays  кол-во дней отпуска
     * @return начисленные отпускные
     */

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacationPayCalculatorResponse> calculate(
            @RequestParam(value = "averageSalary")
            @Positive(message = "параметр должен быть положительным числом")
            BigDecimal averageSalary,

            @RequestParam(value = "vacationDays")
            @Positive(message = "параметр должен быть положительным числом")
            Long vacationDays

//            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Optional<LocalDate> startDate
    ) {

//        log.info(averageSalary.toString());
//        log.info(vacationDays.toString());
//        log.info(startDate.toString());

        String message = String.format(serviceMessages.getVacationPayCalculatorResponseMessageTemplate(),
                averageSalary,
                vacationDays);
//        String message = String.format("Средняя зп: [%s], дней отпуска: [%s]", averageSalary, vacationDays);

//        if (startDate.isPresent()) {
//            vacationDays = countDaysWithoutHolidays.calculate(vacationDays, startDate.get());
//            log.info("Actual days is {}", vacationDays);
//        }

        BigDecimal vacationPay = calculatorService.calculate(averageSalary, vacationDays);


        return new ResponseEntity<>(VacationPayCalculatorResponse.builder()
                .message(message)
                .vacationPayWithoutNdfl(vacationPay.toString())
                .build(), HttpStatus.OK);

    }
}
