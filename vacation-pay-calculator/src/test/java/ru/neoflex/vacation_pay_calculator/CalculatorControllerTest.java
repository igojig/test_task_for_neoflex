package ru.neoflex.vacation_pay_calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.neoflex.vacation_pay_calculator.configuration.ServiceMessages;
import ru.neoflex.vacation_pay_calculator.controlers.CalculatorController;
import ru.neoflex.vacation_pay_calculator.services.CalculatorService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CalculatorController.class)
public class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorService calculatorService;

    @MockBean
    private ServiceMessages serviceMessages;

    /**
     * @param averageSalary средняя зарплата за 12 месяцев
     * @param vacationDays  кол-во дней отпуска
     * @param vacationPay   ожидаемая сумма отпускных
     */
    @ParameterizedTest
    @CsvSource({"50000, 20, 80000"})
    public void whenGivenCorrectData_thenReturnCorrectResult(BigDecimal averageSalary,
                                                             Long vacationDays,
                                                             BigDecimal vacationPay) throws Exception {
        Mockito.when(calculatorService.calculate(averageSalary, vacationDays)).thenReturn(vacationPay);
        Mockito.when(serviceMessages.getResponseMessageTemplate())
                .thenReturn("Средняя зп: [%s], дней отпуска: [%s]");

        String expectedMessage = String.format("Средняя зп: [%s], дней отпуска: [%s]", averageSalary, vacationDays);
        String expectedVacationPay = vacationPay.toString();

        mockMvc.perform(get("/calculacte")
                        .param("averageSalary", averageSalary.toString())
                        .param("vacationDays", vacationDays.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.vacationPayWithoutNdfl").value(expectedVacationPay));
    }

    /**
     * @param averageSalary средняя зарплата за 12 месяцев
     * @param vacationDays  кол-во дней отпуска
     * @param vacationPay   ожидаемая сумма отпускных
     */
    @ParameterizedTest
    @CsvSource({"50000, 20, 80000"})
    public void whenNotGivenVacationDays_ThenReturnMissingParameterResponse(BigDecimal averageSalary,
                                                                            Long vacationDays,
                                                                            BigDecimal vacationPay) throws Exception {

        mockMvc.perform(get("/calculacte")
                        .param("averageSalary",  averageSalary.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.fieldName").value("vacationDays"))
                .andExpect(jsonPath("$.message").exists());
    }

    /**
     * @param averageSalary средняя зарплата за 12 месяцев
     * @param vacationDays  кол-во дней отпуска
     */
    @ParameterizedTest
    @CsvSource({"50rt000, 20"})
    public void whenGivenInvalidValues_ThenReturnArgumentMismatchResponse(String averageSalary,
                                                                   String vacationDays) throws Exception {

        String expectedMessage = String.format(String.format("Параметр averageSalary:[%s] задан неверно. " +
                "Ожидается число с десятичной точкой и не более 2 десятичных знаков.", averageSalary));

        mockMvc.perform(get("/calculacte")
                        .param("averageSalary", averageSalary)
                        .param("vacationDays", vacationDays)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.fieldName").value("averageSalary"))
                .andExpect(jsonPath("$.invalidValue").value(averageSalary))
                .andExpect(jsonPath("$.message").exists());
    }
}
