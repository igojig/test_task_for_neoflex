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
    private final RoundingMode roundingMode = RoundingMode.HALF_EVEN;


    /**
     *
     * метод рассчитывает значение отпускных по следующей формуле: <br/>
     * {@code averageSalary/numOfDaysInMonth * vacationDays - averageSalary/numOfDaysInMonth * vacationDays * ndflRate}
     *
     * @param averageSalary средняя зарплата за 12 месяцев
     * @param vacationDays количество дней отпуска
     * @return значение отпускных
     */

    @Override
    public BigDecimal calculate(BigDecimal averageSalary, Long vacationDays) {

        BigDecimal averageDailyEarnings = averageSalary
                .divide(serviceConstants.getAverageNumOfDaysInMonth(),
                        serviceConstants.getScaleValue(),
                        roundingMode);
        log.info("Среднедневной заработок: {}", averageDailyEarnings);

        BigDecimal vacationPay = averageDailyEarnings
                .multiply(BigDecimal.valueOf(vacationDays))
                .setScale(serviceConstants.getScaleValue(),
                        roundingMode);
        log.info("Отпускные с НДФЛ: {}", vacationPay);

        BigDecimal incomeTax = vacationPay
                .multiply(serviceConstants.getNdflRate())
                .setScale(serviceConstants.getScaleValue(),
                        roundingMode);
        log.info("НДФЛ: {}", incomeTax);

        BigDecimal vacationPayWithoutNdfl = vacationPay.subtract(incomeTax);
        log.info("Начислено отпускных: {}", vacationPayWithoutNdfl);

        return vacationPayWithoutNdfl;
    }
}
