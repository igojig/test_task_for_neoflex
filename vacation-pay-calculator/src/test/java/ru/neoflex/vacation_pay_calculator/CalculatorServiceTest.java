package ru.neoflex.vacation_pay_calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.vacation_pay_calculator.configuration.ServiceConstants;
import ru.neoflex.vacation_pay_calculator.services.CalculatorServiceImpl;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {

    @Mock
    private ServiceConstants serviceConstants;

    @InjectMocks
    private CalculatorServiceImpl calculatorService;

    /**
     * @param ndflRate                ставка НДФЛ
     * @param averageNumOfDaysInMonth среднее кол-во дней в месяце
     * @param scaleValue              до скольки знаков происходит округление
     * @param averageSalary           средняя зарплата за 12 месяцев
     * @param vacationDays            кол-во дней отпуска
     * @param expectedVacationPay     корректное значение начисленных отпускных
     */
    @ParameterizedTest
    @CsvSource({"0.13, 29.3, 2, 50000, 20, 29692.75",
            "0.13, 29.3, 2, 5773, 7, 1199.91",
            "0.13, 29.3, 2, 87923.34, 20, 52213.92"})
    public void givenCorrectParams_thenResultCorrect(BigDecimal ndflRate,
                                                     BigDecimal averageNumOfDaysInMonth,
                                                     Integer scaleValue,
                                                     BigDecimal averageSalary,
                                                     Long vacationDays,
                                                     BigDecimal expectedVacationPay) {
        Mockito.when(serviceConstants.getNdflRate()).thenReturn(ndflRate);
        Mockito.when(serviceConstants.getAverageNumOfDaysInMonth()).thenReturn(averageNumOfDaysInMonth);
        Mockito.when(serviceConstants.getScaleValue()).thenReturn(scaleValue);

        BigDecimal actual = calculatorService.calculate(averageSalary, vacationDays);

        assertThat(expectedVacationPay).isEqualByComparingTo(actual);
        verify(serviceConstants, times(1)).getAverageNumOfDaysInMonth();
        verify(serviceConstants, times(1)).getNdflRate();
    }

    /**
     * @param ndflRate                ставка НДФЛ
     * @param averageNumOfDaysInMonth среднее кол-во дней в месяце
     * @param averageSalary           средняя зарплата за 12 месяцев
     * @param vacationDays            кол-во дней отпуска
     */
    @ParameterizedTest
    @CsvSource({"0.13, 0, 50000, 20"})
    public void givenZeroAverageDay_shouldThrowArithmeticException(BigDecimal ndflRate,
                                                                   BigDecimal averageNumOfDaysInMonth,
                                                                   BigDecimal averageSalary,
                                                                   Long vacationDays) {
        Mockito.lenient().when(serviceConstants.getNdflRate()).thenReturn(ndflRate);
        Mockito.when(serviceConstants.getAverageNumOfDaysInMonth()).thenReturn(averageNumOfDaysInMonth);

        Assertions.assertThrows(ArithmeticException.class, () -> calculatorService.calculate(averageSalary, vacationDays));
    }


}
