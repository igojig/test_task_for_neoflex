package ru.neoflex.vacation_pay_calculator.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app.messages")
@Validated
public class ServiceMessages {
    private String vacationPayCalculatorResponseMessageTemplate;
}
