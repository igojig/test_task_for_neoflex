package ru.neoflex.vacation_pay_calculator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.vacation_pay_calculator.configuration.ServiceConstants;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

//@Service
//@RequiredArgsConstructor
//public class CountDaysWithoutHolidays {
//
//    private final ServiceConstants serviceConstants;
//
//    public long calculate( Long vacationDays, LocalDate startDate) {
//
//        int holidaysMatchCounter = 0;
//        for (int i = 0; i < vacationDays; i++) {
//            LocalDate tmp = startDate.plusDays(i);
//            int month = tmp.getMonthValue();
//            int day = tmp.getDayOfMonth();
//            if (serviceConstants.getHolidays().containsKey(month)) {
//                if (serviceConstants.getHolidays().get(month).stream().anyMatch(d -> d == day)) {
//                    holidaysMatchCounter++;
//                }
//            }
//        }
//
//        System.out.println(holidaysMatchCounter);
//
//
//
//
//        return vacationDays-holidaysMatchCounter;
//    }
//
//
//    private Stream<LocalDate> dates() {
//        return Stream.of(LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 1),
//                LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 2),
//                LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 3),
//                LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 4),
//                LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 5),
//                LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 6),
//                LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 8),
//                LocalDate.of(LocalDate.now().getYear(), Month.FEBRUARY, 23),
//                LocalDate.of(LocalDate.now().getYear(), Month.MARCH, 8),
//                LocalDate.of(LocalDate.now().getYear(), Month.MAY, 1),
//                LocalDate.of(LocalDate.now().getYear(), Month.MAY, 9),
//                LocalDate.of(LocalDate.now().getYear(), Month.JUNE, 12),
//                LocalDate.of(LocalDate.now().getYear(), Month.NOVEMBER, 4)
//        );
//    }
//
//}
