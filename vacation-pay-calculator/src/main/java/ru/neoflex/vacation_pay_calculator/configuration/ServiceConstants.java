package ru.neoflex.vacation_pay_calculator.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app.calculate")
@Validated
public class ServiceConstants {
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

    /**
     * до скольки цифр после запятой округлять
     */
    @NotNull
    @Positive
    @Min(0)
    @Max(4)
    private int scaleValue;

//    @NotNull
//    private Map<Integer, List<Integer>> holidays;
//
//    @PostConstruct
//    public void init(){
//        System.out.println(holidays);
//    }
}
