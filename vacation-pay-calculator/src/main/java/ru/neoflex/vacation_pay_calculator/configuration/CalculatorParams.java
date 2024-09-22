package ru.neoflex.vacation_pay_calculator.configuration;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app.calculate")
@Validated
public class CalculatorParams {
    /**
     * ставка НДФЛ
     */
    @NotNull
    @Positive
    private BigDecimal ndflRate;

    /**
     * среднее количество дней в году
     */
    @NotNull
    @Positive
    private BigDecimal averageNumOfDaysInMonth;
}
