package ru.neoflex.vacation_pay_calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.neoflex.vacation_pay_calculator.controlers.CalculatorController;
import ru.neoflex.vacation_pay_calculator.services.CalculatorService;

import java.math.BigDecimal;

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

    @ParameterizedTest
    @CsvSource({"50000, 20, 80000"})
    public void whenGivenCorrectData_thenReturnCorrectResult(BigDecimal averageSalary,
                                                             Long vacationDays,
                                                             BigDecimal payments) throws Exception {
        Mockito.when(calculatorService.calculate(averageSalary, vacationDays)).thenReturn(payments);

        String expectedMessage = String.format("Средняя зп: [%s], дней отпуска: [%s]", averageSalary, vacationDays);
        String expectedPayments = payments.toString();

        mockMvc.perform(get("/calculacte")
                        .param("averageSalary", averageSalary.toString())
                        .param("vacationDays", vacationDays.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.vacationPayWithoutNdfl").value(expectedPayments));
    }

    @ParameterizedTest
    @CsvSource({"50000, 20, 80000"})
    public void whenNotGivenVacationDays_ThenReturnViolationMessage(BigDecimal averageSalary,
                                                                    Long vacationDays,
                                                                    BigDecimal payments) throws Exception {
        Mockito.when(calculatorService.calculate(averageSalary, vacationDays)).thenReturn(payments);

        mockMvc.perform(get("/calculacte")
                        .param("averageSalary", averageSalary.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.violations[0].fieldName").value("calculate.vacationDays"))
                .andExpect(jsonPath("$.violations[0].message").value("параметр vacationDays(кол-во дней отпуска) должен быть задан"));
    }

    @ParameterizedTest
    @CsvSource({"50rt000, 20"})
    public void whenNotGivenIncorrectValues_ThenReturnErrorMessage(String averageSalary,
                                                                   String vacationDays) throws Exception {

        Mockito.when(calculatorService.calculate(new BigDecimal("10"), 10L)).thenReturn(new BigDecimal("10"));

        String expectedMessage = String.format(String.format("Параметр averageSalary:[%s] задан неверно. " +
                "Ожидается число с десятичной точкой и не более 2 десятичных знаков.", averageSalary));

        mockMvc.perform(get("/calculacte")
                        .param("averageSalary", averageSalary)
                        .param("vacationDays", vacationDays)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }


}
