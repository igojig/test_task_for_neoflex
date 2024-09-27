## Тестовое задание для учебного центра Neoflex
Приложение "Калькулятор отпускных".
Микросервис на SpringBoot + Java 11 c одним API:
GET "/calculacte"

Минимальные требования: Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.
Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных с учётом праздников и выходных.

## API
`http://localhost:8080/calculacte`

Параметры запроса:
 - `averageSalary` - средняя зарплата за 12 месяцев
   - число, может содержать десятичную точку и произвольное кол-во знаков после запятой (сервис при вычислениях округляет до двух знаков после запятой)
 - `vacationDays` - количество дней отпуска
   - целое число


`Пример: http://localhost:8080/calculacte&?averageSalary=42500.45&vacationDays=25`

## Возвращаемый результат
```
{
    "message": "Средняя зп: [42500.45], дней отпуска: [25]",
    "vacationPayWithoutNdfl": "31549.03"
}
```

## Сообщения при ошибках
1. Если пропущен один из параметров, например пропущен параметр `averageSalary`:

    `http://localhost:8080/calculacte&?vacationDays=25`

    
```
{
    "status": 400,
    "missingFieldName": "averageSalary",
    "message": "Required request parameter 'averageSalary' for method parameter type BigDecimal is not present"
}
```

2. Если указано невалидное значение:

   `http://localhost:8080/calculacte&?averageSalary=LETTERS&vacationDays=25`

    
```
{
    "status": 400,
    "fieldName": "averageSalary",
    "invalidValue": "LETTERS",
    "message": "Failed to convert value of type 'java.lang.String' to required type 'java.math.BigDecimal'; Character L is neither a decimal digit number, decimal point, nor \"e\" notation exponential mark."
}
```
3. Если указано некорректное значение:
```
{
    "status": 400,
    "violations": [
        {
            "fieldName": "calculate.averageSalary",
            "invalidValue": "-50000",
            "message": "параметр должен быть положительным числом"
        }
    ]
}
```

## Состав проекта
 - `config-server` - Spring Cloud Config Server
 - `eureka-server` - Eureka Server
 - `gateway` - Spring Cloud Gateway
 - `vacation-pay-calculator` - микросервис расчета отпускных

## Установка и запуск
`git clone https://github.com/igojig/test_task_for_neoflex.git`

`mvn clean package`

`mvn -f .\config-server\ spring-boot:run`

`mvn -f .\eureka-server\ spring-boot:run`

`mvn -f .\gateway\ spring-boot:run`

`mvn -f .\vacation-pay-calculator\ spring-boot:run`

## Docker
`git clone https://github.com/igojig/test_task_for_neoflex.git`

`mvn clean package`

`docker-compose up --build -d`


## Стек
Java 11, Spring Boot 3, Spring Cloud, Eureka Server, Spring Cloud Gateway, JUnit, Mockito




