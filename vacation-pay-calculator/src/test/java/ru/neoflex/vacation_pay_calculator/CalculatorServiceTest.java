package ru.neoflex.vacation_pay_calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.vacation_pay_calculator.configuration.CalculatorParams;
import ru.neoflex.vacation_pay_calculator.services.CalculatorServiceImpl;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {

    @Mock
    private CalculatorParams calculatorParams;

    @InjectMocks
    private CalculatorServiceImpl calculatorService;

    /**
     * @param averageSalary - средняя зарплата за 12 месяцев -
     * @param vacationDays  - кол-во дней отпуска
     * @param expected      - корректное значение отпускных
     */
    @ParameterizedTest
    @CsvSource({"0.13, 29.3, 50000, 20, 29692.75", "0.13, 29.3,5773, 7, 1199.91", "0.13, 29.3, 87923.34, 20, 52213.92"})
    public void givenCorrectParams_thenResultCorrect(BigDecimal ndflRate,
                                                     BigDecimal averageNumOfDaysInMonth,
                                                     BigDecimal averageSalary,
                                                     Long vacationDays,
                                                     BigDecimal expected) {
        Mockito.when(calculatorParams.getNdflRate()).thenReturn(ndflRate);
        Mockito.when(calculatorParams.getAverageNumOfDaysInMonth()).thenReturn(averageNumOfDaysInMonth);

        BigDecimal actual = calculatorService.calculate(averageSalary, vacationDays);

        assertThat(expected).isEqualByComparingTo(actual);
        verify(calculatorParams, times(1)).getAverageNumOfDaysInMonth();
        verify(calculatorParams, times(1)).getNdflRate();
    }

    @ParameterizedTest
    @CsvSource({"0.13, 0, 50000, 20"})
    public void givenZeroAverageDay_shouldThrowArithmeticException(BigDecimal ndflRate,
                                                                   BigDecimal averageNumOfDaysInMonth,
                                                                   BigDecimal averageSalary,
                                                                   Long vacationDays) {
        Mockito.lenient().when(calculatorParams.getNdflRate()).thenReturn(ndflRate);
        Mockito.when(calculatorParams.getAverageNumOfDaysInMonth()).thenReturn(averageNumOfDaysInMonth);

        Assertions.assertThrows(ArithmeticException.class, () -> calculatorService.calculate(averageSalary, vacationDays));
    }


}
