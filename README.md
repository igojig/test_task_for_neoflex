## Тестовое задание для учебного центра Neoflex
Приложение "Калькулятор отпускных".
Микросервис на SpringBoot + Java 11 c одним API:
GET "/calculacte"

Минимальные требования: Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.
Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных с учётом праздников и выходных.

## API
endpoint: `http://localhost:8080/calculacte`

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
        "violations": [
            {
                "fieldName": "calculate.averageSalary",
                "message": "параметр averageSalary(средняя зарплата) должен быть задан"
            }
        ]
    }
```

2. Если указано невалидное значение, например

   `http://localhost:8080/calculacte&?averageSalary=LETTERS&vacationDays=25`

    
```
   {
     "errorCode": 400,
     "message": "Параметр averageSalary:[LETTERS] задан неверно. Ожидается число с десятичной точкой и не более 2 десятичных знаков."
   }
```
    


## Состав проекта
 - `config-server` - Spring Cloud Config Server
 - `eureka-server` - Eureka Server
 - `gateway` - Spring Cloud Gateway
 - `vacation-pay-calculator` - микросервис расчета отпускных

## Стек
Java 11, Spring Boot 3, Spring Cloud, Eureka Server, Spring Cloud Gateway, JUnit, Mockito

