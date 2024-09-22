package ru.neoflex.vacation_pay_calculator.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.vacation_pay_calculator.configuration.ServiceConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {

    private final ServiceConstants serviceConstants;

    @Override
    public BigDecimal calculate(BigDecimal averageSalary, Long vacationDays) {

        BigDecimal averageDailyEarnings = averageSalary.divide(serviceConstants.getAverageNumOfDaysInMonth(), 2, RoundingMode.HALF_EVEN);
        log.info("Среднедневной заработок {}", averageDailyEarnings);

        BigDecimal vacationPay = averageDailyEarnings.multiply(BigDecimal.valueOf(vacationDays)).setScale(2, RoundingMode.HALF_EVEN);
        log.info("Отпускные с НДФЛ {}", vacationPay);

        BigDecimal incomeTax = vacationPay.multiply(serviceConstants.getNdflRate()).setScale(2, RoundingMode.HALF_EVEN);
        log.info("НДФЛ {}", incomeTax);

        BigDecimal vacationPayWithoutNdfl = vacationPay.subtract(incomeTax);
        log.info("Начислено отпускных {}", vacationPayWithoutNdfl);

        return vacationPayWithoutNdfl;
    }
}
